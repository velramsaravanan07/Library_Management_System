package com.library.impl;

import com.library.dto.Reservation;
import com.library.dto.ReservationStatus;
import com.library.repoistory.ReservationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InMemoryReservationRepository implements ReservationRepository {
    private static final Logger LOGGER = Logger.getLogger(InMemoryReservationRepository.class.getName());
    private final Map reservations = new HashMap();
    private long nextId = 1;

    @Override
    public void addReservation(Reservation reservation) {
        if (reservation == null) {
            LOGGER.log(Level.SEVERE, "Attempted to add null reservation");
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        if (reservation.getReservationId() == 0) {
            reservation.setReservationId(nextId);
            nextId++;
        }
        reservations.put(reservation.getReservationId(), reservation);
        LOGGER.log(Level.INFO, "Reservation added with ID: " + reservation.getReservationId());
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        if (reservation == null) {
            LOGGER.log(Level.SEVERE, "Attempted to update with null reservation");
            throw new IllegalArgumentException("Reservation to update cannot be null");
        }
        if (reservations.containsKey(reservation.getReservationId())) {
            reservations.put(reservation.getReservationId(), reservation);
            LOGGER.log(Level.INFO, "Reservation updated with ID: " + reservation.getReservationId());
            return reservation;
        }
        LOGGER.log(Level.WARNING, "Reservation not found for update with ID: " + reservation.getReservationId());
        return null;
    }

    @Override
    public Reservation findById(long reservationId) {
        Reservation reservation = (Reservation) reservations.get(reservationId);
        if (reservation != null) {
            LOGGER.log(Level.INFO, "Retrieved reservation with ID: " + reservationId);
        } else {
            LOGGER.log(Level.WARNING, "Reservation not found with ID: " + reservationId);
        }
        return reservation;
    }

    @Override
    public List findByPatronId(long patronId) {
        List patronReservations = new ArrayList();
        for (Object reservationObj : reservations.values()) {
            Reservation reservation = (Reservation) reservationObj;
            if (reservation.getPatronId() == patronId) {
                patronReservations.add(reservation);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + patronReservations.size() + " reservations for patron ID: " + patronId);
        return patronReservations;
    }

    @Override
    public List findByBookId(long bookId) {
        List bookReservations = new ArrayList();
        for (Object reservationObj : reservations.values()) {
            Reservation reservation = (Reservation) reservationObj;
            if (reservation.getBookId() == bookId) {
                bookReservations.add(reservation);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + bookReservations.size() + " reservations for book ID: " + bookId);
        return bookReservations;
    }

    @Override
    public List findByStatus(ReservationStatus status) {
        if (status == null) {
            LOGGER.log(Level.WARNING, "Attempted to find reservations with null status");
            return new ArrayList();
        }
        List statusReservations = new ArrayList();
        for (Object reservationObj : reservations.values()) {
            Reservation reservation = (Reservation) reservationObj;
            if (reservation.getStatus() == status) {
                statusReservations.add(reservation);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + statusReservations.size() + " reservations with status: " + status);
        return statusReservations;
    }

    @Override
    public List findExpiredReservations() {
        List expiredReservations = new ArrayList();
        LocalDateTime now = LocalDateTime.now();
        for (Object reservationObj : reservations.values()) {
            Reservation reservation = (Reservation) reservationObj;
            if (reservation.getStatus() == ReservationStatus.ACTIVE && reservation.getExpirationDate() != null && now.isAfter(reservation.getExpirationDate())) {
                expiredReservations.add(reservation);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + expiredReservations.size() + " expired reservations");
        return expiredReservations;
    }
}