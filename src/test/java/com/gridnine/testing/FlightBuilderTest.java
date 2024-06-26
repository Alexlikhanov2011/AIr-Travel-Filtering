package com.gridnine.testing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.gridnine.testing.FlightBuilder.createFlight;
import static com.gridnine.testing.FlightServiceTest.flightWithTwoHourGroundTime;
import static com.gridnine.testing.FlightServiceTest.threeDaysFromNow;
import static org.junit.jupiter.api.Assertions.*;

class FlightBuilderTest {
    @Test
    @DisplayName("When createFlight method has odd number of parameters then IllegalArgumentException")
    void createFlightException() {
        assertThrows(IllegalArgumentException.class,
                () -> createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(4), threeDaysFromNow.plusHours(7)));
    }
    @Test
    @DisplayName("When createFlight works correctly, then the actual flight is equal expected flight flightWithTwoHourGroundTime")
    void createFlightTest() {
        Flight actualFlight = createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2), threeDaysFromNow.plusHours(4), threeDaysFromNow.plusHours(6));
        assertEquals(flightWithTwoHourGroundTime, actualFlight);
    }
}