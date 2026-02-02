# Demo Script (minute-by-minute)
0:00 Start API, run `scripts/reset_demo.sh`.
0:30 Launch Android app in `insecureDebug` and login as userA.
1:00 Open notes list and note detail, show suite_id + key_version.
1:30 Show Settings: token storage hint and simulated risk toggle.
2:00 Burp: tamper a notes request (succeeds in insecure).
3:00 Burp: change /notes/:id to userB note (IDOR success).
4:00 Switch to `secureRelease`, login again.
4:30 Burp: tampering now fails (invalid signature).
5:30 Burp: IDOR blocked with 403; show security events list.
6:30 Show attachment stored encrypted in secure build.
