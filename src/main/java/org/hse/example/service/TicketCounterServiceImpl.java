package org.hse.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Сервис для подсчёта счастливых билетов
 */
public class TicketCounterServiceImpl implements TicketService {
    private final Map<Integer, List<Integer>> summs = new HashMap<>();
    private int maxNumber;
    private boolean done = false;
    private Predicate<Integer> condition = integer -> true;

    /**
     * @param digitsQnty количество цифр в билете
     */
    TicketCounterServiceImpl(int digitsQnty) {
        if (digitsQnty <= 0 || digitsQnty % 2 != 0) {
            throw new IllegalArgumentException("Передан некорректный параметр! " + digitsQnty);
        }
        this.maxNumber = (int) (Math.pow(10, digitsQnty / 2) - 1);
    }

    public TicketCounterServiceImpl(int digitsQnty, Predicate<Integer> condition) {
        this(digitsQnty);
        this.condition = condition;
    }

    /**
     * @return экземпляр {@link TicketCounterServiceImpl} в данном случае this
     */
    @Override
    public TicketService doWork() {
        if (done) {
            throw new IllegalStateException("Уже выполнено!");
        }
        //Заполняем, если нужно, промежуточную структуру пустым списком
        IntStream.rangeClosed(0, maxNumber)
                .forEach(this::processNumber);
        done = true;

        return this;
    }

    @Override
    public void printResult() {
        if (!done) {
            throw new IllegalStateException("Нечего выводить!");
        }
        System.out.println("Всего счастливых билетов получилось " + this.getTicketsQuantity());
    }

    /**
     * Обрабатывает очередной номер сохраняя результат в промежуточную структуру
     *
     * @param number номер
     */
    private void processNumber(int number) {
        int sum = getSumOfDigits(number);
        if (!condition.test(sum)) return;
        summs.putIfAbsent(sum, new ArrayList<>());
        summs.get(sum).add(number);
    }

    /**
     * @param number целое положительное число
     * @return сумма десятичных цифр этого числа
     */
    private int getSumOfDigits(int number) {
        int sum = 0;
        for (int nextNumber = number; nextNumber > 0; nextNumber /= 10) {
            sum += nextNumber % 10;
        }
        return sum;
    }

    /**
     * @return количество счастливых билетов
     */
    private int getTicketsQuantity() {
        return summs.values().stream().mapToInt(integers -> (int) Math.pow(integers.size(), 2)).sum();
    }
}
