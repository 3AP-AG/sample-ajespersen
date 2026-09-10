package ch.threeap.andersfp.guardedblocks;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
  private BlockingQueue<String> drop;

  public Producer(BlockingQueue<String> drop) {
    this.drop = drop;
  }

  public void run() {
    String importantInfo[] = {
      "Mares eat oats", "Does eat oats", "Little lambs eat ivy", "A kid will eat ivy too"
    };
    Random random = new Random();

    for (int i = 0; i < importantInfo.length; i++) {
      try {
        drop.put(importantInfo[i]);
      } catch (InterruptedException e) {
        System.err.format("Producer put was interrupted in for loop: %s%n", e.getMessage());
        throw new RuntimeException(e);
      }
      try {
        Thread.sleep(random.nextInt(5000));
      } catch (InterruptedException e) {
      }
    }
    try {
      drop.put("DONE");
    } catch (InterruptedException e) {
      System.err.format("Producer put DONE was interrupted: %s%n", e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
