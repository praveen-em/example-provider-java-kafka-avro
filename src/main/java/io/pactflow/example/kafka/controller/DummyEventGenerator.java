package io.pactflow.example.kafka.controller;


import com.github.javafaker.Faker;
import io.pactflow.example.kafka.kafka.Producer;
import io.pactflow.example.kafka.model.generated.Address;
import io.pactflow.example.kafka.model.generated.ProductEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
			Address.Builder addressBuilder = Address.newBuilder();
			addressBuilder
					.setDoorNumber(Integer.parseInt(faker.address().buildingNumber()))
					.setStreet(faker.address().streetAddress())
					.setPostcode(faker.address().zipCode());

			ProductEvent productEvent = ProductEvent.newBuilder()
					.setId(faker.internet().uuid())
					.setName(faker.commerce().productName())
					.setType(faker.commerce().material())
					.setVersion("v1")
//					.setPrice(ByteBuffer.wrap(faker.commerce().price(1000, 9999).getBytes()))
					.setCreatedOn(faker.date().past(5, TimeUnit.DAYS).toInstant().atZone(ZoneId.of("Europe/London")).toLocalDate())
					.setLocationBuilder(addressBuilder)
					.setRelatedItems(Arrays.asList("someitem", faker.hipster().word(), faker.hipster().word()))
					.setOtherInfo(Map.of("key1", "value1",
							"key2", "value2",
							"key3", "value3"))
					.build();


			log.info("sending random product event to topic: {}", productEvent);
			producer.sendMessage(productEvent);
		}
	}
}