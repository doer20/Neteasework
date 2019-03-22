done=false

host=$1
ports=$2
jarFile=$3

while [[ "$done" = false ]]; do
	for port in $ports; do
		wget http://${host}:${port}/actuator/health >& /dev/null
		if [[ "$?" -eq "0" ]]; then
			done=true
		else
			done=false
			break
		fi
	done
	if [[ "$done" = true ]]; then
        echo connected
        java -jar ${jarFile}
		break;
  fi
	echo -n .
	sleep 1
done