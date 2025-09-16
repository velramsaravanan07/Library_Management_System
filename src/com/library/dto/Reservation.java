package com.library.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private long reservationId;
    private long patronId;
    private long bookId;
    private LocalDateTime reservationDate;
    private LocalDateTime expirationDate;
    private ReservationStatus status;

    public Reservation(long reservationId, long patronId, long bookId) {
        this.reservationId = reservationId;
        this.patronId = patronId;
        this.bookId = bookId;
        this.reservationDate = LocalDateTime.now();
        this.expirationDate = LocalDateTime.now().plusDays(3);
        this.status = ReservationStatus.ACTIVE;
    }

    // Getters and Setters
    public long getReservationId() { return reservationId; }
    public void setReservationId(long reservationId) { this.reservationId = reservationId; }
    public long getPatronId() { return patronId; }
    public void setPatronId(long patronId) { this.patronId = patronId; }
    public long getBookId() { return bookId; }
    public void setBookId(long bookId) { this.bookId = bookId; }
    public LocalDateTime getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDateTime reservationDate) { this.reservationDate = reservationDate; }
    public LocalDateTime getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDateTime expirationDate) { this.expirationDate = expirationDate; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return reservationId == that.reservationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }

    @Override
    public String toString() {
        return String.format("Reservation{id=%d, patronId=%d, bookId=%d, reservationDate=%s, status=%s}",
                reservationId, patronId, bookId, reservationDate.toLocalDate(), status);
    }
}