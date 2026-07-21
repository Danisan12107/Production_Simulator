import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Device> deviceList = new ArrayList<>();
        deviceSetups(deviceList);
    }

    private static void deviceSetups(List<Device> deviceList) {
        Scanner in = new Scanner(System.in);
        System.out.print("Number of devices: ");
        int num = Integer.parseInt(in.nextLine());
        System.out.println("Number of devices: " + num);
        // create a device: name, id, type -> [l,u]
        for(int i = 0; i < num; i++) {
            System.out.print("Device Name: ");
            String name = in.nextLine();
            System.out.print("Number of Device output types: ");
            int numTypes = Integer.parseInt(in.nextLine());
            Device temp = new Device(name, i);
            for(int j = 0; j < numTypes; j++) {
                outputSetups(temp, in);
            }

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