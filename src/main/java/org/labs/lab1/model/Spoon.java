package org.labs.lab1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
@RequiredArgsConstructor
public class Spoon {
    private final int id;
    private final ReentrantLock lock = new ReentrantLock(true);

    public void pickUp() {
        lock.lock();
    }

    public void putDown() {
        lock.unlock();
    }
}
