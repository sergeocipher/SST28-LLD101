import java.util.*;

public class OnboardingService {
    private final FakeDb db;
    private Validator validator;
    private Parser parser;
    private OnboardingPrinter onboardingPrinter;

    public OnboardingService(FakeDb db) { 
        this.db = db; 
        this.validator = new Validator();
        this.parser = new Parser();
        this.onboardingPrinter = new OnboardingPrinter();
    }

    // Intentionally violates SRP: parses + validates + creates ID + saves + prints.
    public void registerFromRawInput(String raw) {
        onboardingPrinter.printInput(raw);
        parser.parse(raw);

        String name = parser.getField("name");
        String email = parser.getField("email");
        String phone = parser.getField("phone");
        String program = parser.getField("program");  

        // Map<String,String> kv = new LinkedHashMap<>();
        // String[] parts = raw.split(";");
        // for (String p : parts) {
        //     String[] t = p.split("=", 2);
        //     if (t.length == 2) kv.put(t[0].trim(), t[1].trim());
        // }

        // String name = kv.getOrDefault("name", "");
        // String email = kv.getOrDefault("email", "");
        // String phone = kv.getOrDefault("phone", "");
        // String program = kv.getOrDefault("program", "");


        // validation inline, printing inline
        // List<String> errors = new ArrayList<>();
        // if (name.isBlank()) errors.add("name is required");
        // if (email.isBlank() || !email.contains("@")) errors.add("email is invalid");
        // if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit)) errors.add("phone is invalid");
        // if (!(program.equals("CSE") || program.equals("AI") || program.equals("SWE"))) errors.add("program is invalid");
        
        List<String> errors = new ArrayList<>();
        if (!errors.isEmpty()) {
            System.out.println("ERROR: cannot register");
            for (String e : errors) System.out.println("- " + e);
            return;
        }


        if (validator.checkName(name)) errors.add("name is required");
        if (validator.checkEmail(email)) errors.add("email is invalid");
        if (validator.checkPhone(phone)) errors.add("phone is invalid");
        if (validator.checkProgram(program)) errors.add("program is invalid");

        if (!errors.isEmpty()) {
            System.out.println("ERROR: cannot register");
            onboardingPrinter.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(db.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);

        db.save(rec);

        // System.out.println("OK: created student " + id);
        // System.out.println("Saved. Total students: " + db.count());
        // System.out.println("CONFIRMATION:");
        // System.out.println(rec);

         onboardingPrinter.printSuccess(id, db.count(), rec);
    }
}
