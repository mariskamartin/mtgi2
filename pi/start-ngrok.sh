~/ngrok http 8080 > /dev/null &

sleep 10

export WEBHOOK_URL="$(curl http://localhost:4040/api/tunnels | jq ".tunnels[0].public_url")"
echo "$WEBHOOK_URL"