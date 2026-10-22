import java.util.*;
import java.sql.SQLException;

public class Main {
    static void main(String[] args) throws SQLException{
        // list of devices that will hold rules of what a device is composed including their types of outputs
        List<Device> deviceList = new ArrayList<>();
        // Begins the setup of the devices the user would like to simulate
        deviceSetups(deviceList);
        for(Device d : deviceList) {

            d.getString();
            deviceInfoInsert(d);

        }

        Simulator sim = new Simulator(deviceList);

    }


    private static void deviceSetups(List<Device> deviceList) {
        Scanner in = new Scanner(System.in);
        System.out.print("Number of devices: ");
        // stores the number of devices the user wants to create / simulate
        int num = Integer.parseInt(in.nextLine());
        System.out.println("Number of devices: " + num);
        // create a device: name, id, type -> [l,u]
        for(int i = 0; i < num; i++) {
            System.out.print("Device Name: ");
            // gets the name of the currently created device
            String name = in.nextLine();
            System.out.print("Number of Device output types: ");
            // gets the number of outputs for the device
            int numTypes = Integer.parseInt(in.nextLine());
            // Creates a new device with its name
            Device temp = new Device(name);
            for(int j = 0; j < numTypes; j++) {
                //sets up each
                outputSetups(temp, in);
            }

            deviceList.add(temp);
        }
    }

    // notes:
    // create new repo to handle the inserts of rules and the getting of rules of a device
    private static void outputSetups(Device temp,Scanner in) {
        // get type: 1 -> category, 2 -> numeric
        while(true) {
            System.out.print("Name of output: ");
            String name = in.nextLine();
            System.out.println("Is Output type categorical or numerical?");
            System.out.print("Enter 1 for Categorical or 2 for Numerical");

            String type = in.nextLine();

            if(type.equals("1")) {
                // build categorical frame
                // list of possible vals for current category type
                List<String> categoryVals = new ArrayList<>();
                System.out.print("Enter number of possible values for category: ");
                int numPossible = Integer.parseInt(in.nextLine());
                for(int i = 0; i < numPossible; i++) {
                    System.out.print("Enter categorical value " + (i + 1) + ": ");
                    String res = in.nextLine();
                    categoryVals.add(res);
                }

                temp.addCategoricOutput(name, categoryVals);
                return;
            } else if(type.equals("2")) {
                // build numerical frame
                System.out.print("Enter lower bound of output");
                int lower = Integer.parseInt(in.nextLine());
                System.out.print("Enter upper bound of output");
                int upper = Integer.parseInt(in.nextLine());
                temp.addNumericOutput(name, lower, upper);
                return;
            } else {
                System.out.println("Entered wrong input try again!");
            }
        }
    }

    private static void deviceInfoInsert(Device device) throws SQLException{
        DeviceRepository dRepo = new DeviceRepository();
        dRepo.insertDevice(device);
        RuleRepository rRepo = new RuleRepository();
        Set<String> keys = device.getOutputNames();
        for(String name : keys) {
            // res -> contians the list of values of possible outputs of a category
            OutputType res = device.getOutput(name);
            if (res.isCategory()) {
                // insert cat rule
                    //things to send:
                        // id
                        // output_name
                        // list of values possible for categorical output ~ rule repo will handle the insert of each output
                rRepo.insertCatRule(device.getId(), name, res.getCategories());
            } else {
                rRepo.insertNumRule(device.getId(),name, res.getLower(), res.getUpper());
            }
        }
    }
}