package com.demo.ordersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Random;

@SpringBootApplication
@EnableScheduling
public class OrdersServiceApplication {
    private final Logger LOG = LoggerFactory.getLogger(OrdersServiceApplication.class);

    private final OrderRepository orderRepository;

    public OrdersServiceApplication(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }

    @Scheduled(fixedRate = 5000)
    public void generateOrder() {
        CustomOrder customOrder = generateRandomOrder();
        LOG.info(customOrder.toString());
        LOG.info("Created new order: {}", customOrder.toString());
        String response = "No Response";
        try {
            orderRepository.save(customOrder);
        } catch (Exception e) {
            LOG.error("Ooops: {}", e.getMessage());
        }

        LOG.info("Response {}", response);
    }

    public static CustomOrder generateRandomOrder() {
        Random random = new Random();

        CustomOrder customOrder = new CustomOrder();
        customOrder.setCustomerName("Customer " + random.nextInt(1000));
        customOrder.setItemName("Item: " + random.nextInt(1000));
        customOrder.setQuantity(random.nextInt(20));

        return customOrder;
    }
}

@Repository
interface OrderRepository extends MongoRepository<CustomOrder, Long> {
}