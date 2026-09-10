package com.ast06.ambulance;

import java.util.*;

public class AmbulanceDispatchService {

    private List<Ambulance> ambulances = new ArrayList<>();

    private PriorityQueue<EmergencyRequest> waitingQueue =
        new PriorityQueue<>(
            Comparator.comparingInt(
                r -> getPriority(r.getEmergencyType())
            )
        );

    private List<EmergencyRequest> history = new ArrayList<>();

    private static int getPriority(
            EmergencyRequest.EmergencyType type) {

        switch (type) {
            case CRITICAL:
                return 1;

            case HIGH:
                return 2;

            case MODERATE:
                return 3;

            case NORMAL:
                return 4;

            default:
                return 5;
        }
    }

    public void addAmbulance(Ambulance ambulance) {
        ambulances.add(ambulance);
    }

    public void submitEmergency(EmergencyRequest request) {

        if (request == null)
            throw new InvalidEmergencyException(
                "Emergency request cannot be null"
            );

        history.add(request);

        waitingQueue.add(request);

        allocateAmbulance();
    }

    public void allocateAmbulance() {

        while (!waitingQueue.isEmpty()) {

            EmergencyRequest request =
                waitingQueue.peek();

            Ambulance selected = null;

            for (Ambulance ambulance : ambulances) {

                if (ambulance.getState()
                        == Ambulance.State.AVAILABLE
                    && ambulance.getType()
                        == request.getAmbulanceType()) {

                    if (selected == null ||
                        ambulance.getDistance()
                        < selected.getDistance()) {

                        selected = ambulance;
                    }
                }
            }

            if (selected == null)
                break;

            waitingQueue.poll();

            selected.setState(
                Ambulance.State.DISPATCHED
            );

            request.assignAmbulance(
                selected.getAmbulanceId()
            );
        }
    }

    public void updateAmbulanceState(
            String ambulanceId,
            Ambulance.State newState) {

        for (Ambulance ambulance : ambulances) {

            if (ambulance.getAmbulanceId()
                    .equals(ambulanceId)) {

                ambulance.setState(newState);

                for (EmergencyRequest request : history) {

                    if (ambulanceId.equals(
                            request.getAmbulanceId())) {

                        if (newState ==
                            Ambulance.State.EN_ROUTE) {

                            request.setStatus(
                                EmergencyRequest.Status.EN_ROUTE
                            );
                        }

                        else if (newState ==
                            Ambulance.State.PATIENT_PICKED_UP) {

                            request.setStatus(
                                EmergencyRequest.Status.PATIENT_PICKED_UP
                            );
                        }

                        else if (newState ==
                            Ambulance.State.HOSPITAL_ARRIVED) {

                            request.setStatus(
                                EmergencyRequest.Status.HOSPITAL_ARRIVED
                            );
                        }
                    }
                }

                if (newState ==
                    Ambulance.State.AVAILABLE) {

                    allocateAmbulance();
                }

                return;
            }
        }

        throw new InvalidEmergencyException(
            "Ambulance not found"
        );
    }

    public double calculateETA(
            double distance,
            double speed) {

        if (distance < 0 || speed <= 0)
            throw new InvalidEmergencyException(
                "Invalid distance or speed"
            );

        return (distance / speed) * 60;
    }

    public int getWaitingCount() {
        return waitingQueue.size();
    }

    public List<EmergencyRequest> getHistory() {
        return history;
    }
}
