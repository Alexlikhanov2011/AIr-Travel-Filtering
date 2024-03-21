package com.gridnine.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.gridnine.testing.FlightService.*;

public class Main {
    static List<Flight> flights = FlightBuilder.createFlights();
    static List<Flight> flightsTo = new ArrayList<>();

    public static void main(String[] args) {

        PrintMenu.printStartMenu();

        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                PrintMenu.printMenu();
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            flightsTo = exclusionOfArrivalsEarlierThanArrivalTime(flights);
                            System.out.println("вылет до текущего момента времени:" + "\n" + flightsTo);
                            break;
                        case 2:
                            flightsTo = departureBeforeNow(flights);
                            System.out.println("имеются сегменты с датой прилёта раньше даты вылета:" + "\n" + flightsTo);
                            break;
                        case 3:
                            flightsTo = waitingTimeOnTheGroundIsLongerThanSpecified(flights);
                            System.out.println("общее время, проведённое на земле превышает два часа:" + "\n" + flightsTo);
                            break;
                        case 0:
                            scanner.close();
                            break label;
                    }
                }
            }
        }
    }
}
