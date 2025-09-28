package org.labs.lab1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@RequiredArgsConstructor
public class Kitchen {
    private final AtomicInteger soups;

    public boolean takeSoup() {
        while (true) {
            int current = soups.get();
            if (current <= 0) {
                return false;
            }
            if (soups.compareAndSet(current, current - 1)) {
                return true;
            }
        }
    }

    public int leftSoup() {
        return soups.get();
    }
}
