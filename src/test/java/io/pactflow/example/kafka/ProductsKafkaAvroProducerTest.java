package io.pactflow.example.kafka;

import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junitsupport.loader.VersionSelector;
import io.pactflow.example.kafka.kafka.AvroMessageBuilder;
import io.pactflow.example.kafka.model.generated.ProductEvent;
import io.pactflow.example.kafka.model.generated.EventType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.messaging.Message;

@Provider("pactflow-example-provider-java-kafka")
@PactBroker(scheme = "https", host = "${PACT_BROKER_HOST}",
        consumerVersionSelectors = {@VersionSelector(tag = "master"), @VersionSelector(tag = "prod")},
        authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}"))
  public class ProductsKafkaAvroProducerTest {

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  void testTemplate(Pact pact, Interaction interaction, PactVerificationContext context) {
    context.verifyInteraction();
  }

  @BeforeEach
  void before(PactVerificationContext context) {
    context.setTarget(new MessageTestTarget());
    System.out.println("GIT_COMMIT" + System.getenv("GIT_COMMIT"));
    System.setProperty("pact.provider.version",
        System.getenv("GIT_COMMIT") == null ? "" : System.getenv("GIT_COMMIT"));
    System.setProperty("pact.provider.tag",
        System.getenv("GIT_BRANCH") == null ? "" : System.getenv("GIT_BRANCH"));
    System.setProperty("pact.verifier.publishResults",
        System.getenv("PACT_BROKER_PUBLISH_VERIFICATION_RESULTS") == null ? "false" : "true");
  }

  @PactVerifyProvider("a product event update")
  public MessageAndMetadata productUpdateEvent() throws Exception {
    ProductEvent productEvent = ProductEvent.newBuilder()
            .setId("id1")
            .setName("product name")
            .setType("product type")
            .setVersion("v1")
            .setEvent(EventType.UPDATED)
            .build();
    Message<ProductEvent> message = new AvroMessageBuilder().withProduct(productEvent).build();
    return generateMessageAndMetadata(message);
  }

  @PactVerifyProvider("a product created event")
  public MessageAndMetadata productCreateEvent() throws Exception {
    ProductEvent productEvent = ProductEvent.newBuilder()
            .setId("5cc989d0-d800-434c-b4bb-b1268499e850")
            .setName("product name")
            .setType("product series")
            .setVersion("v1")
            .setEvent(EventType.CREATED)
            .build();
    Message<ProductEvent> message = new AvroMessageBuilder().withProduct(productEvent).build();
    return generateMessageAndMetadata(message);
  }

  private MessageAndMetadata generateMessageAndMetadata(Message<io.pactflow.example.kafka.model.generated.ProductEvent> message) throws IOException {
    HashMap<String, Object> metadata = new HashMap<String, Object>();
    message.getHeaders().forEach((k, v) -> metadata.put(k, v));
//    ObjectMapper objectMapper = new ObjectMapper();
//    String jsonString = objectMapper.writeValueAsString(message.getPayload().toString());
//    return new MessageAndMetadata(Json.parse(jsonString).asString().getBytes(), metadata);
    return new MessageAndMetadata(encode(message.getPayload(), io.pactflow.example.kafka.model.generated.ProductEvent.SCHEMA$), metadata);
  }

  public static byte[] encode(GenericRecord record, Schema schema) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
    BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);

    datumWriter.write(record, encoder);
    encoder.flush();

    return outputStream.toByteArray();
  }


}
