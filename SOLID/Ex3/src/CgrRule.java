import java.util.*;

public class CgrRule implements EligibilityRule {
    private final RuleConfig config;

    public CgrRule(RuleConfig config) {
        this.config = config;
    }

    @Override
    public Optional<String> validate(StudentProfile s) {
        if (s.cgr < config.minCgr) {
            return Optional.of("CGR below " + config.minCgr);
        }
        return Optional.empty();
    }
}