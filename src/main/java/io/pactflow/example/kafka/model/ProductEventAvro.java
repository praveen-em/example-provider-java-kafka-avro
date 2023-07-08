package io.pactflow.example.kafka.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.pactflow.example.kafka.model.generated.EventType;



@Data
public class ProductEventAvro {
  @JsonFormat( shape = JsonFormat.Shape.STRING)
  private String id;
  private String name;
  private String type;
  private String version;
  private Double price;
  private EventType event;

  public ProductEventAvro() {}

  public ProductEventAvro(String id, String name, String type, String version, EventType event, Double price) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.version = version;
    this.price = price;
    this.event = event;
  }
}