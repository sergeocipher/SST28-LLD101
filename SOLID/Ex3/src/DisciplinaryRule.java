import java.util.Optional;

public class DisciplinaryRule implements EligibilityRule {
    @Override
    public Optional<String> validate(StudentProfile s) {
        if (s.disciplinaryFlag != LegacyFlags.NONE) {
            return Optional.of("disciplinary flag present");
        }
        return Optional.empty();
    }
}