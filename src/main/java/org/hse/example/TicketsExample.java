package org.hse.example;

import org.hse.example.service.NearestTickets;
import org.hse.example.service.TicketCounterServiceImpl;
import org.hse.example.service.TicketServiceBuilder;

import java.util.Optional;

/**
 * Реализация примера со счастливыми билетами
 */
public class TicketsExample {

    /**
     * Точка входа
     *
     * @param args строка аргументов. В настоящее время не используется
     */
    public static void main(String[] args) {
//        System.out.println("Hello world!");
        TicketServiceBuilder builder = () -> 6;
        builder.build().doWork().printResult();

        TicketCounterServiceImpl service = new TicketCounterServiceImpl(6, sum -> sum % 3 == 0);
        service.doWork().printResult();

        NearestTickets ticketSrv = new NearestTickets(6, ticket -> ticket % 19 == 0);
        ticketSrv.doWork().printResult();
    }

}
