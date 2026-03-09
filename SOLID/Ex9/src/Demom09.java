public class Demom09 {
    public static void main(String[] args) {
        System.out.println("=== Evaluation Pipeline ===");
        Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");
        
        PlagiarismService plagiarism = new PlagiarismChecker();
        Grader grader = new CodeGrader();
        ReportService writer = new ReportWriter();
        Rubric rubric = new Rubric();

        EvaluationPipeline pipeline = new EvaluationPipeline(plagiarism, grader, writer, rubric);
       pipeline.evaluate(sub);
    }
}
