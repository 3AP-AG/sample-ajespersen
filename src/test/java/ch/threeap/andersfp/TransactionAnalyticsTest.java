package ch.threeap.andersfp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TransactionAnalyticsTest {

  @Test
  void generateCategoryReport_withCompletedTransactions_computesMetricsAndVips() {
    List<Transaction> transactions =
        List.of(
            new Transaction("1", "cust-1", "electronics", 100.0, Transaction.Status.COMPLETED),
            new Transaction("2", "cust-1", "electronics", 150.0, Transaction.Status.COMPLETED),
            new Transaction("3", "cust-2", "electronics", 50.0, Transaction.Status.COMPLETED),
            new Transaction("4", "cust-2", "groceries", 20.0, Transaction.Status.PENDING));

    Map<String, CategoryMetrics> report =
        TransactionAnalytics.generateCategoryReport(transactions, 200.0);

    assertEquals(1, report.size());
    CategoryMetrics electronics = report.get("electronics");
    assertEquals("electronics", electronics.category());
    assertEquals(300.0, electronics.grossRevenue());
    assertEquals(100.0, electronics.averageOrderValue());
    assertEquals(Set.of("cust-1"), electronics.vipCustomers());
    assertTrue(!report.containsKey("groceries"));
  }

  @Test
  void generateCategoryReport_withNoTransactions_returnsEmptyMap() {
    Map<String, CategoryMetrics> report =
        TransactionAnalytics.generateCategoryReport(List.of(), 100.0);

    assertTrue(report.isEmpty());
  }
}
