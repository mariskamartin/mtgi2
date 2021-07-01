# stop current application
kill -SIGHUP $(cat mtgi2.pid)
sleep 10

# start new
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -Xms256m -Xmx1g -jar mtgi2-0.1.0.jar > app.log 2>&1 &
echo "$!" > mtgi2.pid