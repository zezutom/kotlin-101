package com.tomaszezula.kotlin101.sealed.tutorial.example02.java;

/**
 * Sealed interface Vehicle
 * Credit: <a href="https://www.baeldung.com/java-sealed-classes-interfaces">Baeldung</a>
 */
public sealed interface Vehicle permits Car, Truck {
    String getRegistrationNumber();
}

record Car(String registrationNumber, int numberOfSeats) implements Vehicle {
    @Override
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public int numberOfSeats() {
        return numberOfSeats;
    }
}

record Truck(String registrationNumber, int capacity) implements Vehicle {
    @Override
    public String getRegistrationNumber() {
        return registrationNumber;
    }
}