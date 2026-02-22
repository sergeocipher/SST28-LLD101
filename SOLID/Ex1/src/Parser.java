import java.util.*;

public class Parser{
    Map<String,String> kv;
    
    public Parser(){
        this.kv = new LinkedHashMap<>();
    }

    public void parse(String raw){
        String[] parts = raw.split(";");
        for (String p : parts) {
            String[] t = p.split("=", 2);
            if (t.length == 2) kv.put(t[0].trim(), t[1].trim());
        }
    }

    public String getField(String field){
        return kv.getOrDefault(field, "");
    }
}