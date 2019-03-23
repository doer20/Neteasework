mvn package

cp ./service-registry/target/service-registry-0.0.1-SNAPSHOT.jar ./JARs
cp ./account-service/target/account-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./product-service/target/product-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./cart-service/target/cart-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./order-service/target/order-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./payment-service/target/payment-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./edge-service/target/edge-service-0.0.1-SNAPSHOT.jar ./JARs
cp ./hystrix-dashboard/target/hystrix-dashboard-0.0.1-SNAPSHOT.jar ./JARs
cp ./turbine/target/turbine-0.0.1-SNAPSHOT.jar ./JARs

sh ./run.sh