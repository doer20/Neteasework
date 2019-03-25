nohup java -jar ./JARs/service-registry-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1 > ./JARs/service-registry-peer1.log 2>&1 &
nohup java -jar ./JARs/service-registry-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2 > ./JARs/service-registry-peer2.log 2>&1 &
sh ./wait-for-service.sh localhost 8761 account-service
sh ./wait-for-service.sh localhost 8761 product-service
sh ./wait-for-service.sh localhost 8761 cart-service
sh ./wait-for-service.sh localhost 8761 order-service
sh ./wait-for-service.sh localhost 8761 payment-service
sh ./wait-for-service.sh localhost 8761 edge-service
sh ./wait-for-service.sh localhost 8761 hystrix-dashboard
sh ./wait-for-service.sh localhost 8761 turbine