package io.pactflow.example.kafka.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import io.pactflow.example.kafka.kafka.Producer;
import io.pactflow.example.kafka.model.ProductEventAvro;
import io.pactflow.example.kafka.model.generated.EventType;

@Component
public class DummyEventGenerator {
	private static final Logger log = LoggerFactory.getLogger(DummyEventGenerator.class);

	Faker faker = new Faker();

	@Autowired
	private Producer producer;

	@Value("${SEND_TEST_EVENTS}")
	private boolean sendTestEvents;

	@Scheduled(fixedRate = 3000)
	public void generateProductEvent() {
		log.info("SEND_TEST_EVENTS {}", sendTestEvents);
		if (sendTestEvents) {
			final ProductEventAvro event = new ProductEventAvro(faker.internet().uuid(),
				faker.commerce().productName(),
				faker.commerce().material(), 
				"v1", 
				faker.options().option(EventType.class), 
				Double.parseDouble(faker.commerce().price())
			);	

			log.info("sending random product event to topic: {}", event);
			producer.sendMessage(event);
		}
	}
}