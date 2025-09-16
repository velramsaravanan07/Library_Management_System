package com.library.repoistory;


import com.library.dto.Reservation;
import com.library.dto.ReservationStatus;

import java.util.List;

public interface ReservationRepository {
    void addReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    Reservation findById(long reservationId);
    List findByPatronId(long patronId);
    List findByBookId(long bookId);
    List findByStatus(ReservationStatus status);
    List findExpiredReservations();
}