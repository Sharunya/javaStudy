package org.hse.example.service;

/**
 * Интерфейс-строитель экземпляров {@link TicketService}
 */
public interface TicketServiceBuilder {
    /**
     * @return количество цифр в билете
     */
    Integer produceDigitsQty();

    /**
     * @return экземпляр {@link TicketCounterServiceImpl}
     */
    default TicketService build() {
        return new TicketCounterServiceImpl(produceDigitsQty());
    }
}
