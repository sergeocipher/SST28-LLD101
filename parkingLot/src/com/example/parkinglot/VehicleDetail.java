package com.example.parkinglot;

public class VehicleDetail {
    private final String registrationNumber;
    private final String vehicleCategory;

    public VehicleDetail(String registrationNumber, String vehicleCategory) {
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("registrationNumber is required");
        }
        this.registrationNumber = registrationNumber;
        this.vehicleCategory = vehicleCategory == null ? "UNKNOWN" : vehicleCategory;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getVehicleCategory() {
        return vehicleCategory;
    }

    @Override
    public String toString() {
        return "VehicleDetail{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", vehicleCategory='" + vehicleCategory + '\'' +
                '}';
    }
}
