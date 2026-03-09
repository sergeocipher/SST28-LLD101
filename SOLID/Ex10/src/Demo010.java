public class Demo010 {
    public static void main(String[] args) {
        System.out.println("=== Transport Booking ===");
        TripRequest req = new TripRequest("23BCS1010", new GeoPoint(12.97, 77.59), new GeoPoint(12.93, 77.62));
        DistanceService distance = new DistanceCalculator();
        DriverService driver = new DriverAllocator();
        PaymentService payment = new PaymentGateway();
        TransportBookingService svc = new TransportBookingService(distance, driver, payment);
        svc.book(req);
    }
}
