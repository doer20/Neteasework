done=false

host=$1
ports=$2
service=$3

while [[ "$done" = false ]]; do
	for port in $ports; do
		wget -O ./JARs/health/${service}.health http://${host}:${port}/actuator/health >& /dev/null
		if [[ "$?" -eq "0" ]]; then
			done=true
		else
			done=false
			break
		fi
	done
	if [[ "$done" = true ]]; then
        echo connected
        java -jar ./JARs/${service}-0.0.1-SNAPSHOT.jar > ./JARs/${logFile}.log 2>&1 &
		break;
  fi
	echo -n .
	sleep 1
done