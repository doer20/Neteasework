java -jar ./JARs/service-registry-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1 > service-registry-peer1.log 2>&1 &
java -jar ./JARs/service-registry-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2 > service-registry-peer2.log 2>&1 &
sh ./wait-for-service.sh localhost 8761 ./JARs/account-service-0.0.1-SNAPSHOT.jar account-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/product-service-0.0.1-SNAPSHOT.jar product-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/cart-service-0.0.1-SNAPSHOT.jar cart-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/order-service-0.0.1-SNAPSHOT.jar order-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/payment-service-0.0.1-SNAPSHOT.jar payment-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/edge-service-0.0.1-SNAPSHOT.jar edge-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/hystrix-dashboard-0.0.1-SNAPSHOT.jar hystrix-dashboard.log
sh ./wait-for-service.sh localhost 8761 ./JARs/turbine-0.0.1-SNAPSHOT.jar turbine.log