package org.hse.example.service;

public interface TicketService {
    /**
     * Выполняет необходимые вычисления
     *
     * @return экземпляр {@link TicketService}
     */
    TicketService doWork();

    /**
     * Выводит результат работы объекта
     */
    void printResult();
}
