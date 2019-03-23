cp ./service-registry/target/service-registry-0.0.1-SNAPSHOT.jar ./JARs
cp ./account-service/target/account-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./product-service/target/product-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./cart-service/target/cart-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./order-service/target/order-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./payment-service/target/payment-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./edge-service/target/edge-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./hystrix-dashboard/target/hystrix-dashboard-0.0.1-SNAPSHOT.jar ./JARs
cp ./turbine/target/turbine-0.0.1-SNAPSHOT.jar ./JARs

java -jar ./JARs/service-registry-0.0.1-SNAPSHOT.jar -spring.profiles.active=peer1 > service-registry-peer1.log 2>&1 &
java -jar ./JARs/service-registry-0.0.1-SNAPSHOT.jar -spring.profiles.active=peer2 > service-registry-peer2.log 2>&1 &
./wait-for-service localhost 8761 ./JARs/account-service-0.0.1-SNAPSHOT.jar account-service.log
./wait-for-service localhost 8761 ./JARs/product-service-0.0.1-SNAPSHOT.jar product-service.log
./wait-for-service localhost 8761 ./JARs/cart-service-0.0.1-SNAPSHOT.jar cart-service.log
./wait-for-service localhost 8761 ./JARs/order-service-0.0.1-SNAPSHOT.jar order-service.log
./wait-for-service localhost 8761 ./JARs/payment-service-0.0.1-SNAPSHOT.jar payment-service.log
./wait-for-service localhost 8761 ./JARs/edge-service-0.0.1-SNAPSHOT.jar edge-service.log
./wait-for-service localhost 8761 ./JARs/hystrix-dashboard-0.0.1-SNAPSHOT.jar hystrix-dashboard.log
./wait-for-service localhost 8761 ./JARs/turbine-0.0.1-SNAPSHOT.jar turbine.log