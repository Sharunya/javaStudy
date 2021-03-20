package org.hse.example.service;

/**
 * Создаёт экземпляры {@link NearestTickets}
 */
public interface NearestTicketsBuilder extends TicketServiceBuilder {

    /**
     * @return экземпляр {@link TicketService}
     */
    @Override
    default TicketService build() {
        return new NearestTickets(produceDigitsQty());
    }
}
