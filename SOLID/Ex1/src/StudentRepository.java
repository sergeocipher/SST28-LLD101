import java.util.List;

public interface StudentRepository {
    public void save(StudentRecord rec);
    public int count();
    public List<StudentRecord> all();
}