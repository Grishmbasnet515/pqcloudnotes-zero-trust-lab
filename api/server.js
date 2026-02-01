const express = require("express");
const fs = require("fs");
const path = require("path");

const app = express();
app.use(express.json());

const dataPath = path.join(__dirname, "data.json");
const INSECURE_MODE = process.env.INSECURE_MODE !== "false";

function loadDb() {
  if (!fs.existsSync(dataPath)) {
    const seed = {
      users: [
        { id: "userA", email: "usera@demo.com", password: "passwordA" },
        { id: "userB", email: "userb@demo.com", password: "passwordB" }
      ],
      notes: [
        {
          id: "noteA1",
          ownerId: "userA",
          title: "UserA Note",
          ciphertext: "ciphertext-userA-1",
          createdAt: "2026-02-01T00:00:00Z",
          suiteId: "CLASSICAL",
          keyVersion: 1
        },
        {
          id: "noteB1",
          ownerId: "userB",
          title: "UserB Note",
          ciphertext: "ciphertext-userB-1",
          createdAt: "2026-02-01T00:00:00Z",
          suiteId: "CLASSICAL",
          keyVersion: 1
        }
      ],
      securityEvents: []
    };
    fs.writeFileSync(dataPath, JSON.stringify(seed, null, 2));
  }
  return JSON.parse(fs.readFileSync(dataPath, "utf-8"));
}

function saveDb(db) {
  fs.writeFileSync(dataPath, JSON.stringify(db, null, 2));
}

function recordEvent(db, type, detail) {
  const event = {
    id: `evt_${Date.now()}`,
    type,
    detail,
    createdAt: new Date().toISOString()
  };
  db.securityEvents.unshift(event);
  db.securityEvents = db.securityEvents.slice(0, 50);
  saveDb(db);
}

function findUser(db, email) {
  return db.users.find((u) => u.email.toLowerCase() === email.toLowerCase());
}

function buildSession(user) {
  return {
    userId: user.id,
    accessToken: `access-${user.id}-${Date.now()}`,
    refreshToken: `refresh-${user.id}-${Date.now()}`,
    suiteId: "CLASSICAL",
    keyVersion: 1
  };
}

function getUserIdFromAuth(req) {
  const header = req.headers.authorization || "";
  if (!header.startsWith("Bearer ")) return null;
  const token = header.replace("Bearer ", "");
  if (!token.startsWith("access-")) return null;
  return token.split("-")[1];
}

app.get("/", (req, res) => {
  res.json({ status: "PQCloudNotes API running" });
});

app.get("/health", (req, res) => {
  res.json({ status: "ok" });
});

app.post("/auth/register", (req, res) => {
  const { email, password } = req.body || {};
  if (!email || !password) {
    return res.status(400).json({ error: "missing_fields" });
  }
  const db = loadDb();
  if (findUser(db, email)) {
    return res.status(409).json({ error: "user_exists" });
  }
  const newUser = {
    id: `user${db.users.length + 1}`,
    email,
    password
  };
  db.users.push(newUser);
  saveDb(db);
  return res.json(buildSession(newUser));
});

app.post("/auth/login", (req, res) => {
  const { email, password } = req.body || {};
  if (!email || !password) {
    return res.status(400).json({ error: "missing_fields" });
  }
  const db = loadDb();
  const user = findUser(db, email);
  if (!user || user.password !== password) {
    recordEvent(db, "auth_failed", "invalid_credentials");
    return res.status(401).json({ error: "invalid_credentials" });
  }
  return res.json(buildSession(user));
});

app.post("/auth/refresh", (req, res) => {
  const { refreshToken } = req.body || {};
  if (!refreshToken) {
    return res.status(400).json({ error: "missing_refresh_token" });
  }
  const db = loadDb();
  if (!refreshToken.startsWith("refresh-")) {
    recordEvent(db, "token_expired", "invalid_refresh_token");
    return res.status(401).json({ error: "invalid_refresh_token" });
  }
  const userId = refreshToken.split("-")[1];
  const user = db.users.find((u) => u.id === userId);
  if (!user) {
    return res.status(401).json({ error: "invalid_refresh_token" });
  }
  return res.json(buildSession(user));
});

app.get("/notes", (req, res) => {
  const userId = getUserIdFromAuth(req);
  if (!userId) return res.status(401).json({ error: "unauthorized" });
  const db = loadDb();
  const notes = INSECURE_MODE
    ? db.notes
    : db.notes.filter((note) => note.ownerId === userId);
  return res.json(notes);
});

app.get("/notes/:id", (req, res) => {
  const userId = getUserIdFromAuth(req);
  if (!userId) return res.status(401).json({ error: "unauthorized" });
  const db = loadDb();
  const note = db.notes.find((n) => n.id === req.params.id);
  if (!note) return res.status(404).json({ error: "not_found" });
  if (!INSECURE_MODE && note.ownerId !== userId) {
    recordEvent(db, "idor_blocked", `user:${userId} note:${note.id}`);
    return res.status(403).json({ error: "forbidden" });
  }
  return res.json(note);
});

app.post("/notes", (req, res) => {
  const userId = getUserIdFromAuth(req);
  if (!userId) return res.status(401).json({ error: "unauthorized" });
  const { id, title, ciphertext, suiteId, keyVersion } = req.body || {};
  if (!title || !ciphertext || !suiteId || !keyVersion) {
    return res.status(400).json({ error: "missing_fields" });
  }
  const db = loadDb();
  let note = db.notes.find((n) => n.id === id);
  if (note && !INSECURE_MODE && note.ownerId !== userId) {
    recordEvent(db, "idor_blocked", `user:${userId} note:${note.id}`);
    return res.status(403).json({ error: "forbidden" });
  }
  if (!note) {
    note = {
      id: id || `note_${Date.now()}`,
      ownerId: userId,
      title,
      ciphertext,
      createdAt: new Date().toISOString(),
      suiteId,
      keyVersion
    };
    db.notes.unshift(note);
  } else {
    note.title = title;
    note.ciphertext = ciphertext;
    note.suiteId = suiteId;
    note.keyVersion = keyVersion;
  }
  saveDb(db);
  return res.json(note);
});

const port = process.env.PORT || 4000;
app.listen(port, () => {
  loadDb();
  console.log(`PQCloudNotes API listening on ${port}`);
});
