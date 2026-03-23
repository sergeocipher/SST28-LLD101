package com.example.parkinglot;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DemoParkingLot {
    public static void main(String[] args) {
        int floors = 2;

        Map<SlotType, Integer> slotsPerFloor = new EnumMap<SlotType, Integer>(SlotType.class);
        slotsPerFloor.put(SlotType.SMALL, 3);
        slotsPerFloor.put(SlotType.MEDIUM, 2);
        slotsPerFloor.put(SlotType.LARGE, 1);

        Set<String> entryGates = new HashSet<String>();
        entryGates.add("E1");
        entryGates.add("E2");

        Map<String, Map<String, Integer>> gateDistance = new HashMap<String, Map<String, Integer>>();
        gateDistance.put("E1", new HashMap<String, Integer>());
        gateDistance.put("E2", new HashMap<String, Integer>());

        for (int floor = 1; floor <= floors; floor++) {
            for (int i = 1; i <= 3; i++) {
                String slot = "F" + floor + "-SMALL-" + i;
                gateDistance.get("E1").put(slot, 5 + i + floor);
                gateDistance.get("E2").put(slot, 7 + i + floor);
            }
            for (int i = 1; i <= 2; i++) {
                String slot = "F" + floor + "-MEDIUM-" + i;
                gateDistance.get("E1").put(slot, 8 + i + floor);
                gateDistance.get("E2").put(slot, 4 + i + floor);
            }
            String slot = "F" + floor + "-LARGE-1";
            gateDistance.get("E1").put(slot, 12 + floor);
            gateDistance.get("E2").put(slot, 10 + floor);
        }

        Map<SlotType, Double> rates = new EnumMap<SlotType, Double>(SlotType.class);
        rates.put(SlotType.SMALL, 30.0);
        rates.put(SlotType.MEDIUM, 50.0);
        rates.put(SlotType.LARGE, 80.0);

        ParkingLotService parkingLot = new ParkingLotService(
                floors,
                slotsPerFloor,
                entryGates,
                gateDistance,
                rates
        );

        VehicleDetail vehicle = new VehicleDetail("WB-12-AA-0011", "2-WHEELER");
        ParkingTicket ticket = parkingLot.generateParkingTicket(
                vehicle,
                LocalDateTime.now().minusMinutes(95),
                SlotType.SMALL,
                "E1"
        );

        System.out.println(ticket);
        System.out.println(parkingLot.showStatus());

        Bill bill = parkingLot.generateBill(ticket, LocalDateTime.now());
        System.out.println(bill);
        System.out.println(parkingLot.showStatus());
    }
}
