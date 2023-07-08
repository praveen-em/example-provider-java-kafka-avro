// package io.pactflow.example.kafka;

// import au.com.dius.pact.core.model.Interaction;
// import au.com.dius.pact.core.model.Pact;
// import au.com.dius.pact.provider.MessageAndMetadata;
// import au.com.dius.pact.provider.PactVerifyProvider;
// import au.com.dius.pact.provider.junit5.MessageTestTarget;
// import au.com.dius.pact.provider.junit5.PactVerificationContext;
// import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
// import au.com.dius.pact.provider.junitsupport.Provider;
// import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
// import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
// import au.com.dius.pact.provider.junitsupport.loader.VersionSelector;
// import io.pactflow.example.kafka.kafka.JsonMessageBuilder;
// import io.pactflow.example.kafka.kafka.StringMessageBuilder;
// import io.pactflow.example.kafka.model.ProductEvent;
// import io.pactflow.example.kafka.model.generated.EventType;

// import java.util.HashMap;

// import com.fasterxml.jackson.core.JsonProcessingException;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.TestTemplate;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.messaging.Message;

// @Provider("pactflow-example-provider-java-kafka")
// @PactBroker(scheme = "https", host = "${PACT_BROKER_HOST}",
//         consumerVersionSelectors = {@VersionSelector(tag = "master"), @VersionSelector(tag = "prod")},
//         authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}"))
//   public class ProductsKafkaJsonProducerTest {
//   private static final Logger LOGGER = LoggerFactory.getLogger(ProductsKafkaJsonProducerTest.class);

//   @TestTemplate
//   @ExtendWith(PactVerificationInvocationContextProvider.class)
//   void testTemplate(Pact pact, Interaction interaction, PactVerificationContext context) {
//     context.verifyInteraction();
//   }

//   @BeforeEach
//   void before(PactVerificationContext context) {
//     context.setTarget(new MessageTestTarget());

//     System.out.println("GIT_COMMIT" + System.getenv("GIT_COMMIT"));
//     System.setProperty("pact.provider.version",
//         System.getenv("GIT_COMMIT") == null ? "" : System.getenv("GIT_COMMIT"));
//     System.setProperty("pact.provider.tag",
//         System.getenv("GIT_BRANCH") == null ? "" : System.getenv("GIT_BRANCH"));
//     System.setProperty("pact.verifier.publishResults",
//         System.getenv("PACT_BROKER_PUBLISH_VERIFICATION_RESULTS") == null ? "false" : "true");
//   }

//   @PactVerifyProvider("a product event update")
//   public MessageAndMetadata productUpdateEvent() throws JsonProcessingException {
//     ProductEvent productEvent = new ProductEvent("id1", "product name", "product type", "v1", EventType.UPDATED, 15.00);
//     Message<ProductEvent> message = new JsonMessageBuilder().withProduct(productEvent).build();

//     return generateMessageAndMetadata(message);
//   }

//   @PactVerifyProvider("a product created event")
//   public MessageAndMetadata productCreateEvent() throws JsonProcessingException {
//     ProductEvent productEvent = new ProductEvent("id1", "product name", "product type", "v1", EventType.UPDATED, 15.00);
//     Message<ProductEvent> message = new JsonMessageBuilder().withProduct(productEvent).build();

//     return generateMessageAndMetadata(message);
//   }


//   private MessageAndMetadata generateMessageAndMetadata(Message<ProductEvent> message) {
//     HashMap<String, Object> metadata = new HashMap<String, Object>();
//     message.getHeaders().forEach((k, v) -> metadata.put(k, v));

//     return new MessageAndMetadata(message.getPayload().toString().getBytes(), metadata);
//   }
// }
