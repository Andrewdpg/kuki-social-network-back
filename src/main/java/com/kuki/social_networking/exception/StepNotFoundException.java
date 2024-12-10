package com.kuki.social_networking.exception;

import java.util.UUID;

/**
 * Exception thrown when a specified step is not found in the system.
 * This exception extends {@link RuntimeException}, indicating that it is an unchecked exception.
 */
public class StepNotFoundException extends RuntimeException {
  public StepNotFoundException(String message) {
    super(message);
  }
}
