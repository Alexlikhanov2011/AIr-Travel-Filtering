package com.gridnine.testing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.gridnine.testing.FlightBuilder.createFlight;
import static org.junit.jupiter.api.Assertions.*;

class FlightServiceTest {
    public static LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
    public static Segment normalSegment = new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2));
    public static Segment normalSegment2 = new Segment(threeDaysFromNow.plusHours(4), threeDaysFromNow.plusHours(6));
    public static Segment normalSegment3 = new Segment(threeDaysFromNow.plusHours(7), threeDaysFromNow.plusHours(8));
    public static Segment departureInThePastSegment = new Segment(threeDaysFromNow.minusDays(4), threeDaysFromNow.minusDays(4).plusHours(2));
    public static Segment arrivalBeforeDepartureSegment = new Segment(threeDaysFromNow, threeDaysFromNow.minusHours(6));
    public static Segment arrivalEqualDepartureSegment = new Segment(threeDaysFromNow, threeDaysFromNow);
    public static Flight flightWithOneNormalSegment = new Flight(List.of(normalSegment));
    public static Flight flightWithThreeHourGroundTime = new Flight(List.of(normalSegment, normalSegment2, normalSegment3));
    public static Flight flightWithTwoHourGroundTime = new Flight(List.of(normalSegment, normalSegment2));
    public static Flight flightWithDepartureInThePast = new Flight(List.of(departureInThePastSegment, normalSegment));
    public static Flight flightWithArrivalEqualDeparture = new Flight(List.of(arrivalEqualDepartureSegment));
    public static Flight flightWithArrivalBeforeDepartureSegment = new Flight(List.of(arrivalBeforeDepartureSegment));

    List<Flight> expectedFlights = Arrays.asList(flightWithTwoHourGroundTime,
            flightWithArrivalEqualDeparture,
            flightWithOneNormalSegment);

    @Test
    @DisplayName("when the filterDepartingInThePast works correctly, then the filtered flights are equal expected flights")
    void filterDepartingInThePast() {
        List<Flight> flights = Arrays.asList(flightWithDepartureInThePast,
                flightWithTwoHourGroundTime,
                flightWithArrivalEqualDeparture,
                flightWithOneNormalSegment);
        assertEquals(expectedFlights, FlightService.exclusionOfArrivalsEarlierThanArrivalTime(flights));
    }

    @Test
    @DisplayName("when the filterArrivalBeforeDeparture works correctly, then the filtered flights are equal expected flights")
    void filterArrivalBeforeDeparture() {
        List<Flight> flights = Arrays.asList(flightWithArrivalBeforeDepartureSegment,
                flightWithTwoHourGroundTime,
                flightWithArrivalEqualDeparture,
                flightWithOneNormalSegment);
        assertEquals(expectedFlights, FlightService.departureBeforeNow(flights));
    }

    @Test
    @DisplayName("when the filterMoreThanTwoHoursGroundTime works correctly, then the filtered flights are equal expected flights")
    void filterMoreThanTwoHoursGroundTime() {
        List<Flight> flights = Arrays.asList(flightWithThreeHourGroundTime,
                flightWithTwoHourGroundTime,
                flightWithArrivalEqualDeparture,
                flightWithOneNormalSegment);
        assertEquals(expectedFlights, FlightService.waitingTimeOnTheGroundIsLongerThanSpecified(flights));
    }
}