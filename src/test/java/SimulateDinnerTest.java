import org.junit.jupiter.api.Test;
import org.labs.lab1.SimulateDinner;
import org.labs.lab1.dto.ResultDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulateDinnerTest {

    @Test
    void whenSimulateDinnerWithDefaultParams_thenSuccess() throws InterruptedException {
        int philosopherCount = 7;
        int waiterCount = 1;
        int soups = 1_000_000;

        startDinner(philosopherCount, waiterCount, soups);
    }

    @Test
    void whenSimulateDinnerWithBigWaitersAndSmallPhilosophers_thenSuccess() throws InterruptedException {
        int philosopherCount = 7;
        int waiterCount = 100;
        int soups = 1_000_000;

        startDinner(philosopherCount, waiterCount, soups);
    }

    @Test
    void whenSimulateDinnerWithSmallWaitersAndBigPhilosophers_thenSuccess() throws InterruptedException {
        int philosopherCount = 100;
        int waiterCount = 7;
        int soups = 1_000_000;

        startDinner(philosopherCount, waiterCount, soups);
    }

    @Test
    void whenSimulateDinnerWithSmallSoups_thenSuccess() throws InterruptedException {
        int philosopherCount = 10;
        int waiterCount = 7;
        int soups = 100;

        startDinner(philosopherCount, waiterCount, soups);
    }

    private void startDinner(int philosopherCount, int waiterCount, int soups) throws InterruptedException {
        SimulateDinner simulateDinner = new SimulateDinner(philosopherCount, waiterCount, soups);

        ResultDto result = simulateDinner.startDinner();

        assertEquals(0, result.getLeftSoups());
    }
}
