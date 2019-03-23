java -jar ./JARs/service-registry-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1 > ./JARs/service-registry-peer1.log 2>&1 &
java -jar ./JARs/service-registry-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2 > ./JARs/service-registry-peer2.log 2>&1 &
sh ./wait-for-service.sh localhost 8761 ./JARs/account-service-0.0.1-SNAPSHOT.jar ./JARs/account-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/product-service-0.0.1-SNAPSHOT.jar ./JARs/product-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/cart-service-0.0.1-SNAPSHOT.jar ./JARs/cart-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/order-service-0.0.1-SNAPSHOT.jar ./JARs/order-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/payment-service-0.0.1-SNAPSHOT.jar ./JARs/payment-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/edge-service-0.0.1-SNAPSHOT.jar ./JARs/edge-service.log
sh ./wait-for-service.sh localhost 8761 ./JARs/hystrix-dashboard-0.0.1-SNAPSHOT.jar ./JARs/hystrix-dashboard.log
sh ./wait-for-service.sh localhost 8761 ./JARs/turbine-0.0.1-SNAPSHOT.jar ./JARs/turbine.log