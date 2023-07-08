package io.pactflow.example.kafka.kafka;

import org.springframework.messaging.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.pactflow.example.kafka.model.ProductEventAvro;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;

public class StringMessageBuilder {
  private ObjectMapper mapper = new ObjectMapper();
  private ProductEventAvro productEvent;

  public StringMessageBuilder withProduct(ProductEventAvro productEvent) {
    this.productEvent = productEvent;
    return this;
  }

  public Message<String> build() throws JsonProcessingException {
    return MessageBuilder
        .withPayload(mapper.writeValueAsString(this.productEvent))
        .setHeader(KafkaHeaders.TOPIC, "products-string")
        .setHeader("Content-Type", "application/json; charset=utf-8")
        .build();
  }

}