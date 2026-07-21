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

    public void getString() {
        System.out.println("Device Name: " + name);
        System.out.println("Device Id: " + id);
        for(String key : outputTypes.keySet()) {
            System.out.println("Output name: " + key);
            OutputType temp = outputTypes.get(key);
            System.out.println();
            if(temp.isNumeric()) {
                System.out.println(key + " value bounds:");
                System.out.println("    Lower Bound: " + temp.getLower());
                System.out.println("    Upper Bound " + temp.getUpper());
            } else {
                System.out.println(key + " category dedicated values: ");
                for(String s : temp.getCategories()) {
                    System.out.println("    ~" + s);
                }
            }
        }
    }

}
