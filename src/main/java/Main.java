import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // list of devices that will hold rules of what a device is composed including their types of outputs
        List<Device> deviceList = new ArrayList<>();
        // Begins the setup of the devices the user would like to simulate
        deviceSetups(deviceList);
        for(Device d : deviceList) {
            d.getString();
        }

        Simulator(deviceList);

//        String sql = "SELECT * FROM Devices";
//
//        try (
//                Connection connection = DatabaseManager.getConnection();
//                Statement statement = connection.createStatement();
//                ResultSet result = statement.executeQuery(sql)
//        ) {
//
//            System.out.println("Connected successfully!");
//            System.out.println("Database: " + connection.getCatalog());
//
//            while (result.next()) {
//
//                int id = result.getInt("device_id");
//                String name = result.getString("device_name");
//                System.out.println(id + " | " + name + " | ");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        // sql setup
        
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
            // Creates a new device with its name and id
            Device temp = new Device(name, i);
            for(int j = 0; j < numTypes; j++) {
                //sets up each
                outputSetups(temp, in);
            }

            deviceList.add(temp);
        }
    }

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
                    categoryVals.add(in.nextLine());
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
}