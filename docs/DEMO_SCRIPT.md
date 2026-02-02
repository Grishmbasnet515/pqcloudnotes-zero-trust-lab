# Demo Script (placeholder)
1. Start API and reset demo data.
2. Launch Android app in insecureDebug.
3. Login as userA and show notes list.
4. Use Burp to tamper request (works in insecure).
5. Change note id to show IDOR.
6. Switch to secureRelease and repeat (tampering blocked, IDOR blocked).
