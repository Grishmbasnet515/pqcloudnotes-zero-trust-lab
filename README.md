# pqcloudnotes-lab
PQCloudNotes is a student-friendly mobile + cloud security lab focused on crypto agility, request signing, IDOR prevention, and secure storage.

## Overview
PQCloudNotes simulates a cloud-synced encrypted notes app that can switch crypto suites (CLASSICAL vs HYBRID_PQ_READY) and supports key rotation. It demonstrates how mobile and backend controls work together to reduce blast radius and to prepare for a post-quantum migration path.
This repository includes intentionally vulnerable and secure build flavors for student pentesting practice.

## Threat model (demo focus)
- Request tampering and replay
- IDOR (insecure direct object reference)
- Weak token storage on device
- Encrypted vs plaintext local data at rest
- Compromised device signals and server enforcement

## Architecture (ASCII)
```
Android (Kotlin + Compose)
  | Retrofit + OkHttp (signing, risk headers)
  | Room cache
  v
Node.js Express API (auth + notes + events)
  | JSON DB (resettable)
  v
Security events log
```

## Setup
Backend:
1. `cd api`
2. `npm install`
3. `npm start`

Android:
1. Open project in Android Studio.
2. Use flavor `insecureDebug` or `secureRelease`.
3. Emulator access: use `http://10.0.2.2:4000` for API base URL.

Scripts:
- `scripts/reset_demo.sh` resets JSON DB to demo users and notes.
- `scripts/curl_examples.sh` shows sample auth + notes calls.

## Demo scripts
See `docs/DEMO_SCRIPT.md`.

## Evidence checklist
See `evidence/README.md`.

## What I learned (internship-ready)
- API auth + session management
- Refresh token rotation patterns
- Request signing and anti-replay
- IDOR prevention
- Logging and monitoring mindset
- Mobile secure storage and encrypted local caching

## Notes
- HYBRID_PQ_READY suite uses simulated PQ secrets for demo purposes.
- Insecure flavor intentionally includes weaknesses for student labs.

## Final checklist
- Android Kotlin + Compose only
- Insecure vs secure flavors with intentional vulns + fixes
- Room cache with insecure plaintext demo vs secure ciphertext
- Backend Express API with auth, notes, security events
- Request signing + anti-replay in secure
- IDOR demo (insecure) and prevention (secure)
- Token storage: weak vs encrypted
- Attachments: plaintext vs encrypted
- Root/risk headers with server enforcement
