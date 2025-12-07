
#!/bin/bash
URL="http://localhost:8080/api"

echo "=== TEST JPA ==="

echo ""
echo "1. Envoyer à Bob..."
curl -X POST "$URL/chat/send" \
  -H "Content-Type: application/json" \
  -d '{"destinataire":"bob","auteur":"alice","contenu":"Message 1"}'

echo ""
echo "2. Recevoir Bob..."
curl -X POST "$URL/chat/receive/bob?timeout=2000"

echo ""
echo "3. Envoyer à Alice..."
curl -X POST "$URL/chat/send" \
  -H "Content-Type: application/json" \
  -d '{"destinataire":"alice","auteur":"bob","contenu":"Bonjour"}'

echo ""
echo "4. Recevoir Alice..."
curl -X POST "$URL/chat/receive/alice?timeout=2000"

echo ""
echo "=== FIN ==="