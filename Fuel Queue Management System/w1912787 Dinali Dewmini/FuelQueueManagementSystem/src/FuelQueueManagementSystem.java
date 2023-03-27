import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
public abstract class FuelQueueManagementSystem {
    public static int FuelStock=6600;
    public static String[][] queues = new String[3][6];
    public static int qtyMissing=0;
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Initialization(queues);
        while (true){
            System.out.println("""
                    --------------------------------------------Options--------------------------------------------
                    100 or VFQ: View all Fuel Queues.
                    101 or VEQ: View all Empty Queues.
                    102 or ACQ: Add customer to a Queue.
                    103 or RCQ: Remove a customer from a Queue. (From a specific location)
                    104 or PCQ: Remove a served customer.
                    105 or VCS: View Customers Sorted in alphabetical order
                    106 or SPD: Store Program Data into file.
                    107 or LPD: Load Program Data from file.
                    108 or STK: View Remaining Fuel Stock.
                    109 or AFS: Add Fuel Stock.
                    999 or EXT: Exit the Program.
                    ------------------------------------------------------------------------------------------------""");
            System.out.println("Enter the Option:");
            String option = scanner.nextLine();
            switch (option) {
                case "100", "VFQ" -> ViewAllFuelQueues(queues);
                case "101", "VEQ" -> ViewAllEmptyQueues(queues);
                case "102", "ACQ" -> AddCustomerToaQueue(queues);
                case "103", "RCQ" -> RemoveACustomerFromAQueue(queues);
                case "104", "PCQ" -> RemoveAServedCustomer(queues);
                case "105", "VCS" -> ViewCustomersSortedInAlphabeticalOrder(queues);
                case "106", "SPD" -> StoreProgramDataIntoFile(queues);
                case "107", "LPD" -> LoadProgramDataFromFile(queues);
                case "108", "STK" -> ViewRemainingFuelStock();
                case "109", "AFS" -> AddFuelStock();
                case "999", "EXT" -> System.exit(0);
            }
        }
    }
    //methods
    private static void Initialization(String[][] queues) {
        for (int x = 0; x < 3; x++) {
            for(int y=0;y<6;y++) {
                queues[x][y] = "empty";
            }
        }
    }
    private static void ViewAllEmptyQueues(String[][] queues) {
        for (int x = 0; x < 3; x++) {
            if (queues[x][0].equals("empty")) {
                System.out.println("queue 0" + (x+1) + "is empty");
            }
        }
    }
    public static void ViewAllFuelQueues(String[][] queues) {
        for (int x = 0; x < 3; x++) {
            System.out.println();
            System.out.println("..queue 0" + (x+1) + "..");
            System.out.println();
            for (int y = 0; y < 6; y++) {
                System.out.println("customer" + (y+1) + ":"+queues[x][y]);
            }
        }
    }
    public static void AddCustomerToaQueue(String[][]queues){

        System.out.println("Enter the queue number:");
        int queueNo = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the customer name :");
        String CName = scanner.nextLine();
        for (int y = 0; y < 6; y++){
            if (queues[queueNo-1][y].equals("empty")){
                queues[queueNo-1][y]=CName;
                break;
            }
        }
    }
    public static void RemoveACustomerFromAQueue(String[][]queues){
        System.out.println("Enter the queue number:");
        int queueNo = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the customer slot number to remove:");
        int y= Integer.parseInt(scanner.nextLine());
        while (y<5){
            queues[queueNo-1][y-1]=queues[queueNo-1][y];
            queues[queueNo-1][5]="empty";
            y++;
        }
    }
    public static void RemoveAServedCustomer(String[][]queues){
        System.out.println("Enter the queue number:");
        int queueNo = Integer.parseInt(scanner.nextLine());
        if (queues[queueNo-1][0].equals("empty")){
            System.out.println("This queue is already empty");
        }else{
            int y = 0;
            while (y<5){
                queues[queueNo-1][y]=queues[queueNo-1][y+1];
                queues[queueNo-1][5]="empty";
                y++;
            }
            FuelStock -= 10;
            qtyMissing += 10;
            if (FuelStock<=500);{
                System.out.println("------WARNING------");
                System.out.println(" FUEL STOCK IS LOWER THAN 500 LITERS , PLEASE RESTOCK THE FUEL STOCK");
            }
        }
    }
    public static void ViewCustomersSortedInAlphabeticalOrder(String[][]queues){
        ArrayList<String> newQueue = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            newQueue.addAll(Arrays.asList(queues[i]).subList(0, 6));
        }
        newQueue.removeAll(Collections.singleton("empty"));
        bubbleSort(newQueue);
        System.out.println();
        System.out.println("Customers In Alphabetical Order: ");
        System.out.println();
        for (String s : newQueue) {
            for (int count1 = 0; count1 < 3; count1++) {
                for (int count2 = 0; count2 < 6; count2++) {
                    if (queues[count1][count2].equals(s)) {
                        System.out.println(s);
                    }
                }
            }
        }
    }
    public static void bubbleSort(ArrayList<String> newQueue){
        //BubbleSort
        for (int x=0;x<3;x++){
            int n=newQueue.size();
            String temp;
            for(int c=0;c<n;c++){
                for(int j=1;j<(n-c);j++){
                    if(newQueue.get(j-1).compareTo(newQueue.get(j)) > 0){
                        //swap elements
                        temp = newQueue.get(j-1);
                        newQueue.set(j-1,newQueue.get(j));
                        newQueue.set(j,temp);
                    }
                }
            }
        }
    }
    public static void StoreProgramDataIntoFile(String[][]queues){
        try {
            FileWriter myWriter = new FileWriter("D:\\SE\\year01\\sem02\\SD 2 pro\\New folder\\w1912787 Dinali Dewmini\\FuelQueueManagementSystem\\src\\DataFile");
            for(int i=0;i<3;i++){
                for(int c=0; c<6;c++){
                    myWriter.write(queues[i][c]+"\n");
                }
            }
            myWriter.close();
            System.out.println("Successfully stored.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void LoadProgramDataFromFile(String[][]queues){
        try {
            File myObj = new File("D:\\SE\\year01\\sem02\\SD 2 pro\\New folder\\w1912787 Dinali Dewmini\\FuelQueueManagementSystem\\src\\DataFile");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                for(int i=0;i<3;i++){
                    for(int c=0; c<6;c++){
                        queues[i][c] = myReader.nextLine();
                    }
                }
                System.out.println("Successfully Loaded Data from File");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void ViewRemainingFuelStock(){
        System.out.println("Remaining Fuel Stock:"+FuelStock);
    }
    public static void AddFuelStock(){
        //Validate Fuel Qty entered.
        System.out.println();
        int additionalFuel;
        do {
            System.out.println("Enter the number of liters of Fuel to be restock: ");
            additionalFuel = scanner.nextInt();
            if (additionalFuel <= qtyMissing) {
                FuelStock += additionalFuel;
                System.out.println("New fuel Quantity: " + FuelStock + ".");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Quantity Added: " + additionalFuel + " liters exceeds the addable quantity of " + qtyMissing + " litres.");
            }
        }while(additionalFuel > qtyMissing);
    }
}
