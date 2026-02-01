const express = require("express");
const fs = require("fs");
const path = require("path");

const app = express();
app.use(express.json());

const dataPath = path.join(__dirname, "data.json");

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

app.get("/", (req, res) => {
  res.json({ status: "PQCloudNotes API running" });
});

app.get("/health", (req, res) => {
  res.json({ status: "ok" });
});

const port = process.env.PORT || 4000;
app.listen(port, () => {
  loadDb();
  console.log(`PQCloudNotes API listening on ${port}`);
});
