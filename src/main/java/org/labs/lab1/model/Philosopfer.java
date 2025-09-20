package org.labs.lab1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class Philosopfer implements Runnable {
    private final int id;
    private final Spoon leftSpoon;
    private final Spoon rightSpoon;
    private final BlockingQueue<Request> queue;
    private final AtomicBoolean running;
    private final CyclicBarrier barrier;
    private int totalHasEaten = 0;

    @Override
    public void run() {
        try {
            int phase = 0;
            while (running.get()) {
                think();

                if ((phase % 2 == 0 && id % 2 == 0)
                        || (phase % 2 != 0 && id % 2 != 0)) {

                    if (!takeNewSoup()) {
                        running.set(false);
                        break;
                    }

                    eat();
                }

                barrier.await();
                phase++;
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }

//        log.info("Философ №{} съел {} тарелок порций супа", id, totalHasEaten);
    }

    public int getTotalHasEaten() {
        return totalHasEaten;
    }

    private boolean takeNewSoup() {
        try {
            Request request = new Request(id);
            queue.put(request);
            Boolean success = request.completed.get();

            if (success != null && success) {
//                log.info("Философ №{} получил новую тарелку супа", id + 1);
                return true;
            } else {
                running.set(false);
                return false;
            }

        } catch (Exception e) {
//            log.warn("Филосов №{} не смог получить новую тарелку супа", id + 1);
        }

        return false;
    }

    private void eat() {
        Spoon first = (leftSpoon.getId() < rightSpoon.getId()) ? leftSpoon : rightSpoon;
        Spoon second = (first == leftSpoon) ? rightSpoon : leftSpoon;

        first.pickUp();
        second.pickUp();

//        log.info("Философ №{} принимает пищу", id);
        totalHasEaten++;
        second.putDown();
        first.putDown();
    }

    private void think() {
//            log.info("Философ №{} думает", id);
//            Thread.sleep(10);
    }
}
