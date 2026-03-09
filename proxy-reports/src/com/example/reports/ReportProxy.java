package com.example.reports;

public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private RealReport realReport;   // cached real object

    private final AccessControl accessControl = new AccessControl();

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {

        // Access check
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("ACCESS DENIED for " + user.getName()
                    + " to report " + reportId);
            return;
        }

        // Lazy loading
        if (realReport == null) {
            realReport = new RealReport(reportId, title, classification);
        }

        realReport.display(user);
    }
}
