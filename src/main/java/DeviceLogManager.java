import java.util.*;
public class DeviceLogManager {
    private Map<Integer, List<DeviceLog>> map;
    public DeviceLogManager() {
        map = new HashMap<>();
    }

    // fix this behavior here since resets every time a device or item has a new log
    public void addDeviceLog(int id, DeviceLog d) {
        if(!map.containsKey(id)) {
            List<DeviceLog> temp = new ArrayList<>();
            temp.add(d);
            map.put(id, temp);
        } else {
            map.get(id).add(d);
        }
    }

    public void removeDevice(int id) {
        if(!map.containsKey(id)) {
            System.err.println("Device not found");
        }
        map.remove(id);
    }
}

