package ch.threeap.andersfp;

import java.util.Set;

public record CategoryMetrics(
    String category, double grossRevenue, double averageOrderValue, Set<String> vipCustomers) {}
