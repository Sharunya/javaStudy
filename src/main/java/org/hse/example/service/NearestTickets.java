package org.hse.example.service;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Находит минимальное расстояние между счастливыми билетами
 */
public class NearestTickets implements TicketService {
    private int maxNumber;
    private int[] digits;
    private boolean done = false;
    private int ticket = 0;
    private int distance;
    private Predicate<Integer> condition = integer -> true;

    /**
     * @param digitsQnty количество цифр в билете
     */
    NearestTickets(int digitsQnty) {
        if (digitsQnty <= 0 || digitsQnty % 2 != 0) {
            throw new IllegalArgumentException("Передан некорректный параметр! " + digitsQnty);
        }
        this.maxNumber = (int) (Math.pow(10, digitsQnty) - 1);
        this.digits = new int[digitsQnty];
        this.distance = this.maxNumber;
    }

    public NearestTickets(int digitsQnty, Predicate<Integer> condition) {
        this(digitsQnty);
        this.condition = condition;
    }

    /**
     * Выполняет необходимые вычисления
     *
     * @return экземпляр {@link TicketService}
     */
    @Override
    public TicketService doWork() {
        if (done) {
            throw new IllegalStateException("Уже выполнено!");
        }

        IntStream.rangeClosed(1, this.maxNumber)
                .filter(this::testPredicat)
                .filter(this::isLucky)
                .forEach(this::processNumber);
        done = true;
        return this;
    }

    private void processNumber(int currentTicket) {
        int currentDistance = currentTicket - ticket;
        if (currentDistance < distance) {
            distance = currentDistance;
            ticket = currentTicket;
        }
    }

    /**
     * @param ticket номер проверяемого билета
     * @return true, если билет счастливый
     */
    private boolean isLucky(int ticket) {
        Arrays.fill(this.digits, 0);
        for (int i = 0, nextNumber = ticket; nextNumber > 0; nextNumber /= 10, i++) {
            this.digits[i] = nextNumber % 10;
        }

        final int firstSum = Arrays.stream(this.digits, 0, this.digits.length / 2).sum();
        final int lastSum = Arrays.stream(this.digits, this.digits.length / 2, this.digits.length).sum();
        return lastSum == firstSum;
    }

    /**
     * Выводит результат работы объекта
     */
    @Override
    public void printResult() {
        if (!done) {
            throw new IllegalStateException("Нечего выводить!");
        }

        String formattedTicket = "%0" + this.digits.length + "d\t";
        System.out.printf("Минимальное расстояние %d\t" + formattedTicket + formattedTicket,
                this.distance,
                (this.ticket - this.distance),
                this.ticket
        );
    }

    private boolean testPredicat(int sum) {
        return condition.test(sum);
    }
}
