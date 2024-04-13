package ch.zhaw.eventsourcing;

import ch.zhaw.eventsourcing.events.Event;
import ch.zhaw.eventsourcing.events.ReservationCancelledEvent;
import ch.zhaw.eventsourcing.events.ReservationCreatedEvent;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class ReservationService {
    private final ReservationEventStore reservationRepository;

    public ReservationService(ReservationEventStore eventstore) {
        this.reservationRepository = eventstore;
    }

    public int soldTicketsByEventId(String eventId) {
        var allEvents = reservationRepository.getEventsByShowId(eventId);
        var idsOfCancelledReservations = getEventStreamOfSubtype(allEvents, ReservationCancelledEvent.class)
                .map(ReservationCancelledEvent::getReservationId)
                .toList();

        return getEventStreamOfSubtype(allEvents, ReservationCreatedEvent.class)
                .filter(event -> !idsOfCancelledReservations.contains(event.getReservation().getReservationId()))
                .mapToInt(event -> event.getReservation().getNumberOfTickets())
                .sum();
    }

    public void cancelReservation(String reservationId) {
        var eventId = reservationRepository.getShowIdByReservationId(reservationId);
        reservationRepository.addEvent(eventId, new ReservationCancelledEvent(UUID.fromString(reservationId)));
    }

    public Reservation createReservation(Reservation reservation) {
        reservationRepository.addEvent(reservation.getShowId(), new ReservationCreatedEvent(reservation));
        return reservation;
    }

    public Collection<Event> findAllEvents() {
        return reservationRepository.getAllEvents();
    }

    private static <T extends Event> Stream<T> getEventStreamOfSubtype(List<Event> allEvents, Class<T> classToFilter) {
        return allEvents.stream()
                .filter(classToFilter::isInstance)
                .map(classToFilter::cast);
    }
}
