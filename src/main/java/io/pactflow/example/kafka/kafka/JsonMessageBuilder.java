package io.pactflow.example.kafka.kafka;

import org.springframework.messaging.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.pactflow.example.kafka.model.ProductEventAvro;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;

public class JsonMessageBuilder {
  private ObjectMapper mapper = new ObjectMapper();
  private ProductEventAvro productEvent;

  public JsonMessageBuilder withProduct(ProductEventAvro productEvent) {
    this.productEvent = productEvent;
    return this;
  }

  public Message<ProductEventAvro> build() throws JsonProcessingException {
    return MessageBuilder
        .withPayload(this.productEvent)
        .setHeader(KafkaHeaders.TOPIC, "products-json")
        .setHeader("Content-Type", "application/vnd.schemaregistry.v1+json; charset=utf-8")
        .build();
  }

}