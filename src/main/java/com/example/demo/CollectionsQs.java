package com.example.demo;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

import java.util.*;

public class CollectionsQs {
  // Exercises
  // Write a program that prints its arguments in random order. Do not make a copy of the argument
  // array. Demonstrate how to print out the elements using both streams and the traditional
  // enhanced for statement.
  public static void randomargs(String[] args) {
    List<String> argList = Arrays.asList(args);
    Collections.shuffle(argList);
    argList.stream().forEach(l -> System.out.println(l));
  }

  // Take the FindDups example and modify it to use a SortedSet instead of a Set.
  // Specify a Comparator so that case is ignored when sorting and identifying set elements.
  public static void finddups(String[] args) {
    SortedSet<String> s = new TreeSet<String>(CASE_INSENSITIVE_ORDER);
    for (String a : args) s.add(a);
    System.out.println(s.size() + " distinct words: " + s);
  }

  // Write a method that takes a List<String> and applies String.trim to each element.
  public static List<String> trimStrings(List<String> lst) {
    lst.stream().map(s -> s.trim()).toList();
  }

  // Consider the four core interfaces, Set, List, Queue, and Map. For each of the following four
  // assignments, specify which of the four core interfaces is best-suited, and explain how to use
  // it to implement the assignment.
  // Whimsical Toys Inc (WTI) needs to record the names of all its employees. Every month, an
  // employee will be chosen at random from these records to receive a free toy.
  // WTI has decided that each new product will be named after an employee but only first names will
  // be used, and each name will be used only once. Prepare a list of unique first names.
  // WTI decides that it only wants to use the most popular names for its toys. Count up the number
  // of employees who have each first name.
  // WTI acquires season tickets for the local lacrosse team, to be shared by employees. Create a
  // waiting list for this popular sport.
  public static void main(String[] args) {
    //        randomargs(args);
    finddups(args);
  }
}
