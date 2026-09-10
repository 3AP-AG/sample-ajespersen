package ch.threeap.andersfp.guardedblocks;

import java.util.concurrent.ArrayBlockingQueue;

public class ProducerConsumerExample {
  public static void main(String[] args) {
    var queue = new ArrayBlockingQueue(10, true);
    (new Thread(new Producer(queue))).start();
    (new Thread(new Consumer(queue))).start();
  }
}
