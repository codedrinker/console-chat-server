git pull
mvn clean compile -DskipTests package
pid=$(ps aux | grep "console-chat-server" | grep -v grep | awk '{print $2}')
echo "kill -9 $pid"
kill -9 $pid
nohup java -jar target/console-chat-server-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
