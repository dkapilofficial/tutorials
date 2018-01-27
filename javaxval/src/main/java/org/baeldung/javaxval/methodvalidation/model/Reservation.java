package org.baeldung.javaxval.methodvalidation.model;

import org.baeldung.javaxval.methodvalidation.constraints.ConsistentDateParameters;
import org.baeldung.javaxval.methodvalidation.constraints.ValidReservation;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
public class Reservation {

    private LocalDate begin;

    private LocalDate end;

    private Customer customer;

    private int room;

    @ConsistentDateParameters
    @ValidReservation
    public Reservation(LocalDate begin, LocalDate end, Customer customer, int room) {
        this.begin = begin;
        this.end = end;
        this.customer = customer;
        this.room = room;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
