package com.example.parkinglot;

import java.time.LocalDateTime;

public class Bill {
    private final String ticketId;
    private final LocalDateTime inTime;
    private final LocalDateTime outTime;
    private final long parkedMinutes;
    private final double amount;

    public Bill(String ticketId, LocalDateTime inTime, LocalDateTime outTime, long parkedMinutes, double amount) {
        this.ticketId = ticketId;
        this.inTime = inTime;
        this.outTime = outTime;
        this.parkedMinutes = parkedMinutes;
        this.amount = amount;
    }

    public String getTicketId() {
        return ticketId;
    }

    public LocalDateTime getInTime() {
        return inTime;
    }

    public LocalDateTime getOutTime() {
        return outTime;
    }

    public long getParkedMinutes() {
        return parkedMinutes;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "ticketId='" + ticketId + '\'' +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", parkedMinutes=" + parkedMinutes +
                ", amount=" + amount +
                '}';
    }
}
