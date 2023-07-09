package io.pactflow.example.kafka.controller;

import io.pactflow.example.kafka.model.generated.ProductEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pactflow.example.kafka.kafka.Producer;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(value = "/", produces = "application/json; charset=utf-8")
class ProductController {

  private final Producer producer;

  ProductController(Producer producer) {
    this.producer = producer;
  }

  @PostMapping("/products")
  ProductEvent newProduct(@RequestBody ProductEvent newProduct) {
    producer.sendMessage(newProduct);
    return newProduct;
  }
}