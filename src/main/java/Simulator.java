import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
public class Simulator {
    private List<Device> deviceList;
    private Simulator(List<Device> list) {
        deviceList = list;
    }

    public void run() {
        for (Device D : deviceList) {
            //get name
            String name = D.getName();
            for(String outName : D.getOutputNames()) {
                OutputType out = D.getOutput(outName);
                if(out.isNumeric()) {
                    // numeric behavior
                    int generate = generateNum(out);
                } else {
                    // categorical behavior
                    String generate = generateCat(out);
                }
            }
            //for each output type within a device generate a value from
                // - if numerical: between lower and upper bound
                // - if categorical: pick one at random

        }
    }

    // picks a value at random within the bounds of the output limits
    private int generateNum(OutputType out) {
        int lower = out.getLower();
        int upper = out.getUpper();
        int value = ThreadLocalRandom.current()
                .nextInt(lower, upper + 1);
        return value;
    }

    // picks one of the category options at random from within the list of category options
    private String generateCat(OutputType out) {
        List<String> l = out.getCategories();
        int lower = 0;
        int upper = l.size();
        int index = (ThreadLocalRandom.current().nextInt(lower, upper));
        return l.get(index);
    }
}
