package com.example.parkinglot;

import java.util.Collections;
import java.util.Map;

public class ParkingLotStatus {
    private final Map<Integer, Map<SlotType, Integer>> availableSlotsByFloor;

    public ParkingLotStatus(Map<Integer, Map<SlotType, Integer>> availableSlotsByFloor) {
        this.availableSlotsByFloor = availableSlotsByFloor;
    }

    public Map<Integer, Map<SlotType, Integer>> getAvailableSlotsByFloor() {
        return Collections.unmodifiableMap(availableSlotsByFloor);
    }

    @Override
    public String toString() {
        return "ParkingLotStatus{" +
                "availableSlotsByFloor=" + availableSlotsByFloor +
                '}';
    }
}
