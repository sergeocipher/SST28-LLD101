import java.util.*;

public class Validator{

    public Validator(){};

    public boolean checkEmail(String email){
        if(email.isEmpty() || email.isBlank() || !email.contains("@")) return true;
        return false;
    }

    public boolean checkName(String name){
        if(name.isBlank()){
            return true;
        }
        return false;
    }

    public boolean checkPhone(String phone){
        if(phone.isBlank() || !phone.chars().allMatch(Character::isDigit)) return true;
        return false;
    }

    public boolean checkProgram(String program){
        if(!(program.equals("CSE") || program.equals("AI") || program.equals("SWE"))){
             return true;
        }
        return false;
    }
}