import java.util.List;

public class OnboardingPrinter {
    public void printInput(String raw) {
        System.out.println("INPUT: " + raw);
    }

    public void printErrors(List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String error : errors) {
            System.out.println("- " + error);
        }
    }

    public void printSuccess(String studentId, int totalStudents, StudentRecord record) {
        System.out.println("OK: created student " + studentId);
        System.out.println("Saved. Total students: " + totalStudents);
        System.out.println("CONFIRMATION:");
        System.out.println(record);
    }
}