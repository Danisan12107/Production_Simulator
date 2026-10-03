import java.util.*;
import java.sql.Timestamp;

public class DeviceLog {
    private int id;
    private String name;
    private Timestamp ts;
    private Map<String, Integer> outputs;

    public DeviceLog(int id, String name, Timestamp ts) {
        this.id = id;
        this.name = name;
        this.ts = ts;
        outputs = new HashMap<>();
    }
}
