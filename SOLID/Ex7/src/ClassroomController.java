public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

    public void startClass() {
        inputConnector pj = reg.getFirstByCapability(inputConnector.class);
        pj.connectInput("HDMI-1");

        adjustBrightness lights = reg.getFirstByCapability(adjustBrightness.class);
        lights.setBrightness(60);

        adjustTemp ac = reg.getFirstByCapability(adjustTemp.class);
        ac.setTemperatureC(24);

        attendanceScanner scan = reg.getFirstByCapability(attendanceScanner.class);
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        reg.getFirstByCapability(Projector.class).powerOff();
        reg.getFirstByCapability(LightsPanel.class).powerOff();
        reg.getFirstByCapability(AirConditioner.class).powerOff();
    }
}
