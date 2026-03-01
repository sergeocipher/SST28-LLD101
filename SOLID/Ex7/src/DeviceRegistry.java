import java.util.*;

public class DeviceRegistry {
    private final List<Object> devices = new ArrayList<>();
    
    public void add(Object device) {
        devices.add(device);
    }
    public <T> T getFirstByCapability(Class<T> type) {
        for (Object d : devices) {
            if (type.isInstance(d)) {
                return type.cast(d);
            }
        }
        throw new IllegalStateException("Missing device for: " + type.getSimpleName());
    }

    public <T> List<T> getAllByCapability(Class<T> type) {
        List<T> result = new ArrayList<>();

        for (Object d : devices) {
            if (type.isInstance(d)) {
                result.add(type.cast(d));
            }
        }

        return result;
    }
}
