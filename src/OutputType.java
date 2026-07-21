import java.util.*;

public class OutputType {
    private Integer lower;
    private Integer upper;
    private List<String> categoryValues;

    private OutputType(Integer lower, Integer upper, List<String> categoryValues) {
        this.lower = lower;
        this.upper = upper;
        this.categoryValues = categoryValues;
    }

    public static OutputType numeric(int lower, int upper) {
        return new OutputType(lower, upper, null);
    }

    public static OutputType category(List<String> values) {
        return new OutputType(null, null, values);
    }

    public boolean isNumeric() {
        return lower != null && upper != null;
    }

    public boolean isCategory() {
        return categoryValues != null;
    }

    public int getLower() {
        return lower;
    }

    public int getUpper() {
        return upper;
    }

    public List<String> getCategories() {
        return categoryValues;
    }
}