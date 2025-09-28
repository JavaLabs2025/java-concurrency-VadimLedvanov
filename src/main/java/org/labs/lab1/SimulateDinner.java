package org.labs.lab1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.labs.lab1.dto.ResultDto;
import org.labs.lab1.model.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@RequiredArgsConstructor
public class SimulateDinner {
    private final int philosopherCount;
    private final int waiterCount;
    private final int soups;

    public ResultDto startDinner() throws InterruptedException {
        Kitchen kitchen = new Kitchen(new AtomicInteger(soups));
        BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
        AtomicBoolean running = new AtomicBoolean(true);
        CyclicBarrier barrier = new CyclicBarrier(philosopherCount);

        Philosopfer[] philosopfers = new Philosopfer[philosopherCount];
        Spoon[] spoons = new Spoon[philosopherCount];

        for (int i = 0; i < philosopherCount; i++) {
            spoons[i] = new Spoon(i);
        }

        Thread[] waiters = new Thread[waiterCount];
        for (int i = 0; i < waiterCount; i++) {
            waiters[i] = new Thread(new Waiter(i, kitchen, queue));
            waiters[i].start();
        }

        Thread[] treads = new Thread[philosopherCount];
        for (int i = 0; i < philosopherCount; i++) {
            philosopfers[i] = new Philosopfer(i, spoons[i], spoons[(i + 1) % philosopherCount], queue, running);
            treads[i] = new Thread(philosopfers[i]);
        }

        for (int i = 0; i < philosopherCount; i+=2) {
            treads[i].start();
        }

        for (int i = 1; i < philosopherCount; i+=2) {
            treads[i].start();
        }

        while (kitchen.leftSoup() > 0) {
            Thread.sleep(10);
        }

        running.set(false);
        for (Thread w : waiters) {
            w.interrupt();
            w.join();
        }

        for (Thread p : treads) {
            p.interrupt();
            p.join();
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (Philosopfer p : philosopfers) {
            int eaten = p.getTotalHasEaten();
            if (eaten < min) min = eaten;
            if (eaten > max) max = eaten;
        }

        return ResultDto.builder()
                .min(min)
                .max(max)
                .leftSoups(kitchen.leftSoup())
                .build();
    }
}
