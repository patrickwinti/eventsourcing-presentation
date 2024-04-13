package ch.zhaw.eventsourcing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationServiceTest {

    ReservationService reservationService = new ReservationService(new ReservationEventStore());

    @Test
    void soldTicketsByEventId() {
        var reservation1 = reservationService.createReservation(new Reservation("cust 1", "show1", 4));
        reservationService.createReservation(new Reservation("cust 2", "show1", 3));
        reservationService.createReservation(new Reservation("cust 3", "show2", 1));

        assertEquals(7, reservationService.soldTicketsByEventId("show1"));
        assertEquals(1, reservationService.soldTicketsByEventId("show2"));

        reservationService.cancelReservation(reservation1.getReservationId().toString());
        assertEquals(3, reservationService.soldTicketsByEventId("show1"));

        reservationService.findAllEvents().forEach(System.out::println);
    }
}