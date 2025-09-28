package org.labs.lab1;

import lombok.extern.slf4j.Slf4j;
import org.labs.lab1.dto.ResultDto;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int philosopherCount = 7;
        int waiterCount = 2;
        int soups = 10000;

        SimulateDinner simulateDinner = new SimulateDinner(philosopherCount, waiterCount, soups);
        ResultDto resultDto = simulateDinner.startDinner();

        log.info("=== Статистика ===");
        log.info("Минимум съедено: {}", resultDto.getMin());
        log.info("Максимум съедено: {}", resultDto.getMax());
        log.info("Разница: {}", (resultDto.getMax() - resultDto.getMin()));
        log.info("Осталось супов {}", resultDto.getLeftSoups());
    }
}