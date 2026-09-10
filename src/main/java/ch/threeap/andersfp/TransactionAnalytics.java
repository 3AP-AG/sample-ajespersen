package ch.threeap.andersfp;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TransactionAnalytics {

  static class Wrapper {
    private static boolean filterCompleted(Transaction tx) {
      return tx.status() == Transaction.Status.COMPLETED;
    }
  }

  public static Map<String, CategoryMetrics> generateCategoryReport(
      List<Transaction> transactions, double vipThreshold) {

    Set<String> vipCustomerIds =
        transactions.stream()
            .filter(Wrapper::filterCompleted)
            .collect(
                Collectors.groupingBy(
                    Transaction::customerId, Collectors.summingDouble(Transaction::amount)))
            .entrySet()
            .stream()
            .filter(tx -> tx.getValue() >= vipThreshold)
            .map(Map.Entry::getKey)
            .collect(Collectors.toUnmodifiableSet());

    return transactions.stream()
        .filter(Wrapper::filterCompleted)
        .collect(
            Collectors.groupingBy(
                Transaction::category,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    categoryOrders -> {
                      Set<String> categoryVips =
                          categoryOrders.stream()
                              .map(Transaction::customerId)
                              .filter(vipCustomerIds::contains)
                              .collect(Collectors.toUnmodifiableSet());

                      record FinancialStats(double grossRevenue, double averageOrderValue) {}

                      FinancialStats stats =
                          categoryOrders.stream()
                              .collect(
                                  Collectors.teeing(
                                      Collectors.summingDouble(Transaction::amount),
                                      Collectors.averagingDouble(Transaction::amount),
                                      FinancialStats::new));

                      return new CategoryMetrics(
                          categoryOrders.getFirst().category(),
                          stats.grossRevenue,
                          stats.averageOrderValue,
                          categoryVips);
                    })));
  }
}
