package ch.threeap.andersfp.guardedblocks;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
  private BlockingQueue<String> drop;

  public Consumer(BlockingQueue<String> drop) {
    this.drop = drop;
  }

  public void run() {
    Random random = new Random();
    try {
      for (String message = drop.take(); !message.equals("DONE"); message = drop.take()) {
        System.out.format("MESSAGE RECEIVED: %s%n", message);
        try {
          Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
        }
      }
    } catch (InterruptedException e) {
      System.err.format("Take was interrupted in for loop: %s%n", e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
