package com.tarapaca.api.product.infrastructure.exception;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String reference) {
    super("Product not found with reference: " + reference);
  }
}
