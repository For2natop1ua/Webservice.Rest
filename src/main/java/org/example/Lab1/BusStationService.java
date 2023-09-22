package org.example.Lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class BusStationService {

    private static final List<Bus> busList = new ArrayList<>();

    public BusStationService() {

        Bus bus1 = new Bus();
        bus1.setBusNumber("A125");
        bus1.setDepartureLocation("м. Харків");
        bus1.setDestination("м. Полтава");
        bus1.setDepartureTime("09:00");
        bus1.setArrivalTime("12:00");

        Bus bus2 = new Bus();
        bus2.setBusNumber("B307");
        bus2.setDepartureLocation("м. Чернігів");
        bus2.setDestination("м. Київ");
        bus2.setDepartureTime("10:00");
        bus2.setArrivalTime("14:00");

        busList.add(bus1);
        busList.add(bus2);
    }

    public static void main(String[] args) {
        SpringApplication.run(BusStationService.class, args);
    }

    // Для Google Chrome
    @GetMapping("/BusStation")
    public String getAllBuses() {
        StringBuilder result = new StringBuilder();
        for (Bus bus : busList) {
            result.append("Номер: ").append(bus.getBusNumber()).append("<br>");
            result.append("Пункт відправлення: ").append(bus.getDepartureLocation()).append("<br>");
            result.append("Пункт призначення: ").append(bus.getDestination()).append("<br>");
            result.append("Час відправлення: ").append(bus.getDepartureTime()).append("<br>");
            result.append("Час прибуття: ").append(bus.getArrivalTime()).append("<br><br>");
        }
        return result.toString();
    }
    @GetMapping("/BusStation/{busNumber}")
    public ResponseEntity<?> getBusByNumber(@PathVariable String busNumber) {
        try {
            for (Bus bus : busList) {
                if (bus.getBusNumber().equals(busNumber)) {
                    String result = "Номер: " + bus.getBusNumber() + "<br>" +
                            "Пункт відправлення: " + bus.getDepartureLocation() + "<br>" +
                            "Пункт призначення: " + bus.getDestination() + "<br>" +
                            "Час відправлення: " + bus.getDepartureTime() + "<br>" +
                            "Час прибуття: " + bus.getArrivalTime() + "<br><br>";
                    return ResponseEntity.ok(result);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Не знайдено");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Некоректний формат");
        }
    }
    @PostMapping("/BusStation")
    public ResponseEntity<Void> addBus(@RequestBody Bus bus) {
        busList.add(bus);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/BusStation/{busNumber}")
    public ResponseEntity<?> deleteBus(@PathVariable String busNumber) {
        try {
            Bus busToRemove = null;
            for (Bus bus : busList) {
                if (bus.getBusNumber().equals(busNumber)) {
                    busToRemove = bus;
                    break;
                }
            }
            if (busToRemove != null) {
                busList.remove(busToRemove);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Не знайдено");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Некоректний формат");
        }
    }
}
