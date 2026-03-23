package com.example.parkinglot;

public class ParkingSlot {
    private final String slotId;
    private final int floorNumber;
    private final SlotType slotType;
    private boolean occupied;

    public ParkingSlot(String slotId, int floorNumber, SlotType slotType) {
        this.slotId = slotId;
        this.floorNumber = floorNumber;
        this.slotType = slotType;
        this.occupied = false;
    }

    public String getSlotId() {
        return slotId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupy() {
        if (occupied) {
            throw new IllegalStateException("Slot already occupied");
        }
        this.occupied = true;
    }

    public void release() {
        this.occupied = false;
    }

    @Override
    public String toString() {
        return "ParkingSlot{" +
                "slotId='" + slotId + '\'' +
                ", floorNumber=" + floorNumber +
                ", slotType=" + slotType +
                ", occupied=" + occupied +
                '}';
    }
}
