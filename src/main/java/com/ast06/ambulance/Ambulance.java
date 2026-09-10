package com.ast06.ambulance;

public class Ambulance {

    public enum Type {
        BASIC,
        ADVANCED_LIFE_SUPPORT,
        ICU
    }

    public enum State {
        AVAILABLE,
        DISPATCHED,
        EN_ROUTE,
        PATIENT_PICKED_UP,
        HOSPITAL_ARRIVED
    }

    private String ambulanceId;
    private Type type;
    private String driverName;
    private double distance;
    private State state;

    public Ambulance(String ambulanceId, Type type,
                     String driverName, double distance) {

        if (ambulanceId == null || ambulanceId.trim().isEmpty())
            throw new InvalidEmergencyException("Invalid ambulance ID");

        if (driverName == null || driverName.trim().isEmpty())
            throw new InvalidEmergencyException("Invalid driver details");

        if (distance < 0)
            throw new InvalidEmergencyException("Distance cannot be negative");

        this.ambulanceId = ambulanceId;
        this.type = type;
        this.driverName = driverName;
        this.distance = distance;
        this.state = State.AVAILABLE;
    }

    public String getAmbulanceId() {
        return ambulanceId;
    }

    public Type getType() {
        return type;
    }

    public String getDriverName() {
        return driverName;
    }

    public double getDistance() {
        return distance;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
