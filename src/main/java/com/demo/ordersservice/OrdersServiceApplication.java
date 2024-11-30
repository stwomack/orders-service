package com.demo.ordersservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.web.client.RestTemplate;

import java.util.Random;
@SpringBootApplication
@EnableScheduling

public class OrdersServiceApplication {
	private final Logger LOG = LoggerFactory.getLogger(OrdersServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OrdersServiceApplication.class, args);
	}

	@Scheduled(fixedRate
			= 20000)
	public void generateOrder() {
		CustomOrder customOrder = generateRandomOrder();
		System.out.println(customOrder.toString());
		RestTemplate restTemplate = new RestTemplate();
        LOG.info("Created new order: {}", customOrder.toString());
		String response = "No Response";
		try {
			response = restTemplate.postForObject("http://shipping-service/shipping", customOrder, String.class);
		} catch (Exception e) {
			LOG.error("Ooops, service probably down: {}", e.getMessage());
		}

        LOG.info("Response {}", response);
	}

	public static CustomOrder generateRandomOrder() {
		Random random = new Random();

		CustomOrder customOrder = new CustomOrder();
		customOrder.setCustomerName("Customer " + random.nextInt(1000));
		customOrder.setItemName("Item: " + random.nextInt(1000));
		customOrder.setQuantity(+ random.nextInt(20));

		return customOrder;
	}
}
