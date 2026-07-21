import java.util.*;
public class Device {
    private String name;
    private int id;
    // will store category type and lower and upper bounds of cat.
    private Map<String, OutputType> outputTypes;
    public Device(String name, int id) {
        this.name = name;
        this.id = id;
        outputTypes = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void addNumericOutput(String type, int lower, int upper) {
        outputTypes.put(type, OutputType.numeric(lower, upper));
    }

    public void addCategoricOutput(String type, List<String> val) {
        outputTypes.put(type, OutputType.category(val));
    }

    public OutputType getOutput(String type) {
        if(!outputTypes.containsKey(type)) {
            System.err.println("category does not exist");
        }

        return outputTypes.get(type);
    }

}
