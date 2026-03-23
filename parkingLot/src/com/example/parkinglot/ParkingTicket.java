package com.example.parkinglot;

import java.time.LocalDateTime;

public class ParkingTicket {
    private final String ticketId;
    private final VehicleDetail vehicleDetail;
    private final LocalDateTime inTime;
    private final String entryGateId;
    private final ParkingSlot assignedSlot;

    public ParkingTicket(String ticketId, VehicleDetail vehicleDetail, LocalDateTime inTime, String entryGateId, ParkingSlot assignedSlot) {
        this.ticketId = ticketId;
        this.vehicleDetail = vehicleDetail;
        this.inTime = inTime;
        this.entryGateId = entryGateId;
        this.assignedSlot = assignedSlot;
    }

    public String getTicketId() {
        return ticketId;
    }

    public VehicleDetail getVehicleDetail() {
        return vehicleDetail;
    }

    public LocalDateTime getInTime() {
        return inTime;
    }

    public String getEntryGateId() {
        return entryGateId;
    }

    public ParkingSlot getAssignedSlot() {
        return assignedSlot;
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                "ticketId='" + ticketId + '\'' +
                ", vehicleDetail=" + vehicleDetail +
                ", inTime=" + inTime +
                ", entryGateId='" + entryGateId + '\'' +
                ", assignedSlot=" + assignedSlot.getSlotId() +
                '}';
    }
}
