import java.util.*;

public interface EligibilityRule {
    Optional<String> validate(StudentProfile s);
}