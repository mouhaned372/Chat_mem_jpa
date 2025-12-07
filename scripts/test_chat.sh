#!/bin/bash

# Configuration - AJOUTEZ /api car votre app a context-path=/api
BASE_URL="http://localhost:8080/api"
DELAY=1

echo "=============================================="
echo "  TEST COMPLET DU CHAT SERVER SPRING BOOT"
echo "=============================================="
echo ""

# Test de connexion
echo "1. Test de connexion au serveur..."
if curl -s "$BASE_URL" > /dev/null; then
    echo "✅ Serveur accessible à $BASE_URL"
else
    echo "❌ Serveur inaccessible à $BASE_URL"
    echo "   Essayez: curl $BASE_URL"
    exit 1
fi
echo ""

# Envoyer un message
echo "3. Envoi d'un message à $BASE_URL/chat/send..."
response=$(curl -s -X POST "$BASE_URL/chat/send" \
  -H "Content-Type: application/json" \
  -d '{"destinataire": "testuser", "auteur": "system", "contenu": "Test message 1"}')
echo "   Réponse: $response"
echo ""

# Envoyer un deuxième message
echo "4. Envoi deuxième message..."
curl -s -X POST "$BASE_URL/chat/send" \
  -H "Content-Type: application/json" \
  -d '{"destinataire": "testuser", "auteur": "system", "contenu": "Test message 2"}' > /dev/null
echo "✅ Deuxième message envoyé"
echo ""

# Recevoir le premier message
echo "5. Réception premier message (timeout 3s)..."
response=$(curl -s -X POST "$BASE_URL/chat/receive/testuser?timeout=3000")
if [ -n "$response" ] && [ "$response" != "" ] && ! echo "$response" | grep -q "404"; then
    echo "✅ Message reçu: $response"
else
    echo "❌ Aucun message reçu ou erreur"
fi
echo ""

# Recevoir le deuxième message
echo "6. Réception deuxième message (timeout 3s)..."
response=$(curl -s -X POST "$BASE_URL/chat/receive/testuser?timeout=3000")
if [ -n "$response" ] && [ "$response" != "" ] && ! echo "$response" | grep -q "404"; then
    echo "✅ Message reçu: $response"
else
    echo "❌ Aucun message reçu ou erreur"
fi
echo ""

# Test timeout sans message
echo "7. Test timeout sans message..."
response=$(curl -s -X POST "$BASE_URL/chat/receive/nobody?timeout=1000")
if [ -z "$response" ] || echo "$response" | grep -q "204"; then
    echo "✅ Timeout correct (204 No Content)"
else
    echo "❌ Réponse inattendue: $response"
fi
echo ""

echo "=============================================="
echo "  TESTS TERMINÉS"
echo "=============================================="
echo ""
echo "URLs testées:"
echo "  POST $BASE_URL/chat/send"
echo "  POST $BASE_URL/chat/receive/{user}?timeout=X"