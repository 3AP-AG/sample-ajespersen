package ch.threeap.andersfp;

public record Transaction(
    String id, String customerId, String category, double amount, Status status) {
  public enum Status {
    COMPLETED,
    PENDING,
    FAILED,
    REFUNDED
  }
}
