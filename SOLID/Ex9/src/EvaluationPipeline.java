public class EvaluationPipeline {
    // DIP violation: high-level module constructs concretes directly
    private final PlagiarismService plagiarismService;
    private final Grader grader;
    private final ReportService writer;
    private final Rubric rubric;

    public EvaluationPipeline(
            PlagiarismService plagiarismService,
            Grader grader,
            ReportService writer,
            Rubric rubric
    ) {
        this.plagiarismService = plagiarismService;
        this.grader = grader;
        this.writer = writer;
        this.rubric = rubric;
    }

    public void evaluate(Submission sub) {
        // Rubric rubric = new Rubric();
        // PlagiarismChecker pc = new PlagiarismChecker();
        // CodeGrader grader = new CodeGrader();
        // ReportWriter writer = new ReportWriter();

        int plag = plagiarismService.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = grader.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = writer.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}
