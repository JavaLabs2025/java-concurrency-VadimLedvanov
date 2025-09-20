package org.labs.lab1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class Waiter implements Runnable {
    private final int id;
    private final Kitchen kitchen;
    private final BlockingQueue<Request> queue;

    @Override
    public void run() {
        try {
            while (true) {
                Request request = queue.take();

                if (request == null) {
                    continue;
                }

                boolean success = kitchen.takeSoup();
                request.completed.complete(success);

                if (!success && queue.isEmpty() && kitchen.leftSoup() <= 0) {
//                    log.info("На кухне закончились блюда");
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
