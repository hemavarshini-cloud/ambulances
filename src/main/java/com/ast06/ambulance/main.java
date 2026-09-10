package com.ast06.ambulance;

public class Main {

    public static void main(String[] args) {

        AmbulanceDispatchService service =
            new AmbulanceDispatchService();

        Ambulance a1 =
            new Ambulance(
                "AMB101",
                Ambulance.Type.ICU,
                "Ravi",
                5
            );

        Ambulance a2 =
            new Ambulance(
                "AMB102",
                Ambulance.Type.BASIC,
                "Kumar",
                3
            );

        service.addAmbulance(a1);
        service.addAmbulance(a2);

        EmergencyRequest request =
            new EmergencyRequest(
                "P001",
                EmergencyRequest.EmergencyType.CRITICAL,
                "Tiruvannamalai",
                "Apollo Hospital",
                Ambulance.Type.ICU,
                10
            );

        service.submitEmergency(request);

        System.out.println(
            "Patient ID : " +
            request.getPatientId()
        );

        System.out.println(
            "Emergency : " +
            request.getEmergencyType()
        );

        System.out.println(
            "Ambulance : " +
            request.getAmbulanceId()
        );

        System.out.println(
            "ETA : " +
            service.calculateETA(10, 40) +
            " minutes"
        );
    }
}
