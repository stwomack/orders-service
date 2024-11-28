package com.demo.ordersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.web.client.RestTemplate;

import java.util.Random;
@SpringBootApplication
@EnableScheduling

public class OrdersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersServiceApplication.class, args);
	}

	@Scheduled(fixedRate
			= 20000)
	public void generateOrder() {
		Order order = generateRandomOrder();
		System.out.println(order.toString());
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForObject("http://shippping-service", order, String.class);
	}

	public static Order generateRandomOrder() {
		Random random = new Random();

		Order order = new Order();
		order.setId(random.nextLong(10000));
		order.setCustomerName("Customer " + random.nextInt(1000));
		order.setItemName("Item: " + random.nextInt(1000));
		order.setQuantity(+ random.nextInt(20));

		return order;
	}
}
