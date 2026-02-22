import java.util.*;

public class AttendanceRule implements EligibilityRule {
    private final RuleConfig config;

    public AttendanceRule(RuleConfig config) {
        this.config = config;
    }

    @Override
    public Optional<String> validate(StudentProfile s) {
        if (s.attendancePct < config.minAttendance) {
            return Optional.of("attendance below " + config.minAttendance);
        }
        return Optional.empty();
    }
}