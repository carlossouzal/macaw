package edu.maplewood.master_schedule.exception;

public class NoAvailableResource extends RuntimeException {

  public NoAvailableResource(String message) {
    super(message);
  }
}
