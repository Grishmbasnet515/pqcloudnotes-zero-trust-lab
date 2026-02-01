const http = require("http");

const server = http.createServer((req, res) => {
  res.writeHead(200, { "Content-Type": "text/plain" });
  res.end("PQCloudNotes API placeholder\n");
});

const port = process.env.PORT || 4000;
server.listen(port, () => {
  // Placeholder until Express setup in later commits
  console.log(`API placeholder listening on ${port}`);
});
