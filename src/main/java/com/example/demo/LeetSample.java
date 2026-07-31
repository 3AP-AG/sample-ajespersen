package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

public class LeetSample {

  public String frequencySort3(String s) {
    return s
        .chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
        .entrySet()
        .stream()
        .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
        .map(entry -> String.valueOf(entry.getKey()).repeat(entry.getValue().intValue()))
        .collect(Collectors.joining());
  }

  public String frequencySort(String s) {
    var m = s.chars().boxed().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
    var o =
        m.entrySet().stream()
            //                .sorted(Map.Entry.<Character, Long>comparingByValue().reversed());
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
    var ss = o.map(p -> String.valueOf(p.getKey()).repeat(p.getValue().intValue()));
    return ss.collect(Collectors.joining());
  }

  public List<List<String>> groupAnagrams(String[] strs) {
    return Arrays.stream(strs)
        .map(
            x ->
                Map.entry(
                    x,
                    x.chars()
                        .sorted()
                        .mapToObj(c -> String.valueOf((char) c))
                        .collect(Collectors.joining())))
        .collect(
            Collectors.collectingAndThen(
                Collectors.groupingBy(Map.Entry::getKey),
                map ->
                    map.values().stream().map(l -> l.stream().map(Map.Entry::getValue).toList())))
        .toList();
  }
}
