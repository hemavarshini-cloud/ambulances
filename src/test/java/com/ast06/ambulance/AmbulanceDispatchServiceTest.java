package com.ast06.ambulance;

import junit.framework.TestCase;

public class AmbulanceDispatchServiceTest
        extends TestCase {

    private AmbulanceDispatchService service;

    protected void setUp() {

        service =
            new AmbulanceDispatchService();

        service.addAmbulance(
            new Ambulance(
                "A1",
                Ambulance.Type.ICU,
                "Driver1",
                5
            )
        );

        service.addAmbulance(
            new Ambulance(
                "A2",
                Ambulance.Type.ICU,
                "Driver2",
                2
            )
        );
    }

    public void testCriticalEmergency() {

        EmergencyRequest request =
            new EmergencyRequest(
                "P1",
                EmergencyRequest.EmergencyType.CRITICAL,
                "Location",
                "Hospital",
                Ambulance.Type.ICU,
                10
            );

        service.submitEmergency(request);

        assertEquals(
            "A2",
            request.getAmbulanceId()
        );
    }

    public void testWaitingQueue() {

        EmergencyRequest r1 =
            new EmergencyRequest(
                "P1",
                EmergencyRequest.EmergencyType.HIGH,
                "Location",
                "Hospital",
                Ambulance.Type.ICU,
                10
            );

        EmergencyRequest r2 =
            new EmergencyRequest(
                "P2",
                EmergencyRequest.EmergencyType.CRITICAL,
                "Location",
                "Hospital",
                Ambulance.Type.ICU,
                10
            );

        service.submitEmergency(r1);
        service.submitEmergency(r2);

        assertEquals(
            1,
            service.getWaitingCount()
        );
    }

    public void testETA() {

        double eta =
            service.calculateETA(10, 40);

        assertEquals(
            15.0,
            eta,
            0.01
        );
    }

    public void testInvalidDistance() {

        try {

            new EmergencyRequest(
                "P1",
                EmergencyRequest.EmergencyType.NORMAL,
                "Location",
                "Hospital",
                Ambulance.Type.BASIC,
                -5
            );

            fail("Exception expected");

        } catch (InvalidEmergencyException e) {

            assertTrue(true);
        }
    }
}
