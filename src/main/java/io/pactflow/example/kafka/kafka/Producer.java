package io.pactflow.example.kafka.kafka;

import io.pactflow.example.kafka.Application;
import io.pactflow.example.kafka.model.generated.ProductEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Producer {
  @Autowired
  private KafkaTemplate<String, Object> template;
  public static Logger logger = LoggerFactory.getLogger(Application.class);

  public void sendMessage(final ProductEvent productEvent) {
    logger.info("writing product to topic", productEvent);

    try {
      Message<ProductEvent> message = new AvroMessageBuilder().withProduct(productEvent).build();
      this.template.send(message);
    } catch (final Exception e) {
      logger.error("unable to serialise message", e);
    }
  }

}