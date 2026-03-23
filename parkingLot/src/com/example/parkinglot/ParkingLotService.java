package com.example.parkinglot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class ParkingLotService {
    private final List<ParkingSlot> slots;
    private final Set<String> entryGates;
    private final Map<String, Map<String, Integer>> gateSlotDistance;
    private final Map<SlotType, Double> hourlyRate;
    private final Map<String, ParkingTicket> activeTickets;
    private final AtomicLong ticketCounter;

    public ParkingLotService(
            int floorCount,
            Map<SlotType, Integer> slotsPerFloor,
            Set<String> entryGates,
            Map<String, Map<String, Integer>> gateSlotDistance,
            Map<SlotType, Double> hourlyRate
    ) {
        if (floorCount <= 0) {
            throw new IllegalArgumentException("floorCount should be > 0");
        }
        if (slotsPerFloor == null || slotsPerFloor.isEmpty()) {
            throw new IllegalArgumentException("slotsPerFloor is required");
        }
        if (entryGates == null || entryGates.isEmpty()) {
            throw new IllegalArgumentException("entryGates is required");
        }
        this.slots = buildSlots(floorCount, slotsPerFloor);
        this.entryGates = new HashSet<>(entryGates);
        this.gateSlotDistance = gateSlotDistance == null ? new HashMap<String, Map<String, Integer>>() : gateSlotDistance;
        this.hourlyRate = buildRateMap(hourlyRate);
        this.activeTickets = new HashMap<String, ParkingTicket>();
        this.ticketCounter = new AtomicLong(0);
    }

    public ParkingTicket generateParkingTicket(
            VehicleDetail vehicleDetail,
            LocalDateTime inTime,
            SlotType slotType,
            String entryGateId
    ) {
        if (vehicleDetail == null) {
            throw new IllegalArgumentException("vehicleDetail is required");
        }
        if (inTime == null) {
            throw new IllegalArgumentException("inTime is required");
        }
        if (slotType == null) {
            throw new IllegalArgumentException("slotType is required");
        }
        if (entryGateId == null || !entryGates.contains(entryGateId)) {
            throw new IllegalArgumentException("invalid entryGateId");
        }

        ParkingSlot assignedSlot = findNearestAvailableSlot(slotType, entryGateId);
        if (assignedSlot == null) {
            throw new IllegalStateException("No slot available for type: " + slotType);
        }

        assignedSlot.occupy();
        String ticketId = "T" + ticketCounter.incrementAndGet();
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicleDetail, inTime, entryGateId, assignedSlot);
        activeTickets.put(ticketId, ticket);
        return ticket;
    }

    public Bill generateBill(ParkingTicket parkingTicket, LocalDateTime outTime) {
        if (parkingTicket == null) {
            throw new IllegalArgumentException("parkingTicket is required");
        }
        if (outTime == null) {
            throw new IllegalArgumentException("outTime is required");
        }

        ParkingTicket active = activeTickets.get(parkingTicket.getTicketId());
        if (active == null) {
            throw new IllegalArgumentException("Ticket is not active: " + parkingTicket.getTicketId());
        }
        if (outTime.isBefore(active.getInTime())) {
            throw new IllegalArgumentException("outTime cannot be before inTime");
        }

        long minutes = Duration.between(active.getInTime(), outTime).toMinutes();
        if (minutes <= 0) {
            minutes = 1;
        }
        long billedHours = (long) Math.ceil(minutes / 60.0);
        double rate = hourlyRate.get(active.getAssignedSlot().getSlotType());
        double amount = billedHours * rate;

        active.getAssignedSlot().release();
        activeTickets.remove(active.getTicketId());
        return new Bill(active.getTicketId(), active.getInTime(), outTime, minutes, amount);
    }

    public ParkingLotStatus showStatus() {
        Map<Integer, Map<SlotType, Integer>> status = new HashMap<Integer, Map<SlotType, Integer>>();

        for (ParkingSlot slot : slots) {
            if (!status.containsKey(slot.getFloorNumber())) {
                EnumMap<SlotType, Integer> initial = new EnumMap<SlotType, Integer>(SlotType.class);
                for (SlotType type : SlotType.values()) {
                    initial.put(type, 0);
                }
                status.put(slot.getFloorNumber(), initial);
            }

            if (!slot.isOccupied()) {
                Map<SlotType, Integer> floorMap = status.get(slot.getFloorNumber());
                floorMap.put(slot.getSlotType(), floorMap.get(slot.getSlotType()) + 1);
            }
        }

        return new ParkingLotStatus(status);
    }

    private ParkingSlot findNearestAvailableSlot(SlotType slotType, String entryGateId) {
        return slots.stream()
                .filter(slot -> !slot.isOccupied() && slot.getSlotType() == slotType)
                .min(Comparator
                        .comparingInt((ParkingSlot slot) -> distance(entryGateId, slot.getSlotId()))
                        .thenComparingInt(ParkingSlot::getFloorNumber)
                        .thenComparing(ParkingSlot::getSlotId))
                .orElse(null);
    }

    private int distance(String gateId, String slotId) {
        Map<String, Integer> dist = gateSlotDistance.get(gateId);
        if (dist == null) {
            return Integer.MAX_VALUE / 2;
        }
        Integer value = dist.get(slotId);
        if (value == null) {
            return Integer.MAX_VALUE / 2;
        }
        return value;
    }

    private List<ParkingSlot> buildSlots(int floorCount, Map<SlotType, Integer> slotsPerFloor) {
        List<ParkingSlot> built = new ArrayList<ParkingSlot>();
        for (int floor = 1; floor <= floorCount; floor++) {
            for (SlotType type : SlotType.values()) {
                int count = slotsPerFloor.containsKey(type) ? slotsPerFloor.get(type) : 0;
                for (int i = 1; i <= count; i++) {
                    String slotId = "F" + floor + "-" + type.name() + "-" + i;
                    built.add(new ParkingSlot(slotId, floor, type));
                }
            }
        }
        return built;
    }

    private Map<SlotType, Double> buildRateMap(Map<SlotType, Double> configuredRates) {
        EnumMap<SlotType, Double> rates = new EnumMap<SlotType, Double>(SlotType.class);
        rates.put(SlotType.SMALL, 20.0);
        rates.put(SlotType.MEDIUM, 40.0);
        rates.put(SlotType.LARGE, 60.0);
        if (configuredRates != null) {
            rates.putAll(configuredRates);
        }
        return rates;
    }
}
