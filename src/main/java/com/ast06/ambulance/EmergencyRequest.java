package com.ast06.ambulance;

public class EmergencyRequest {

    public enum EmergencyType {
        CRITICAL,
        HIGH,
        MODERATE,
        NORMAL
    }

    public enum Status {
        WAITING,
        DISPATCHED,
        EN_ROUTE,
        PATIENT_PICKED_UP,
        HOSPITAL_ARRIVED,
        COMPLETED
    }

    private String patientId;
    private EmergencyType emergencyType;
    private String pickupLocation;
    private String destinationHospital;
    private Ambulance.Type ambulanceType;
    private double distance;

    private Status status;
    private String ambulanceId;

    public EmergencyRequest(String patientId,
                            EmergencyType emergencyType,
                            String pickupLocation,
                            String destinationHospital,
                            Ambulance.Type ambulanceType,
                            double distance) {

        if (patientId == null || patientId.trim().isEmpty())
            throw new InvalidEmergencyException("Invalid patient ID");

        if (pickupLocation == null || pickupLocation.trim().isEmpty())
            throw new InvalidEmergencyException("Invalid pickup location");

        if (destinationHospital == null ||
            destinationHospital.trim().isEmpty())
            throw new InvalidEmergencyException("Invalid hospital");

        if (distance < 0)
            throw new InvalidEmergencyException("Distance cannot be negative");

        this.patientId = patientId;
        this.emergencyType = emergencyType;
        this.pickupLocation = pickupLocation;
        this.destinationHospital = destinationHospital;
        this.ambulanceType = ambulanceType;
        this.distance = distance;
        this.status = Status.WAITING;
    }

    public String getPatientId() {
        return patientId;
    }

    public EmergencyType getEmergencyType() {
        return emergencyType;
    }

    public Ambulance.Type getAmbulanceType() {
        return ambulanceType;
    }

    public double getDistance() {
        return distance;
    }

    public Status getStatus() {
        return status;
    }

    public String getAmbulanceId() {
        return ambulanceId;
    }

    public void assignAmbulance(String ambulanceId) {
        this.ambulanceId = ambulanceId;
        this.status = Status.DISPATCHED;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
