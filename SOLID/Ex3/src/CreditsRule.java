import java.util.Optional;

public class CreditsRule implements EligibilityRule {
    private final RuleConfig config;

    public CreditsRule(RuleConfig config) {
        this.config = config;
    }

    @Override
    public Optional<String> validate(StudentProfile s) {
        if (s.earnedCredits < config.minCredits) {
            return Optional.of("credits below " + config.minCredits);
        }
        return Optional.empty();
    }
}