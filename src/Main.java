import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    //Initiate variable to get Date and time when a session starts.
    static String sessionStartDateTime;
    //Set up the number of burgers in stock.
    int BurgerStockCount = 50;
    //Set variable to track the number of burgers sold.
    int totalBurgersSold = 0;
    // Three queues to store customer names
    String[] queue1 = new String[2];
    String[] queue2 = new String[3];
    String[] queue3 = new String[5];
    // Multidimensional array to store served customer data
    Object[][] servedCustomerData = new Object[10][4];
    // Flag to control program execution
    boolean runProgramme;


    public static void main(String[] args) {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Save the session start date and time
        sessionStartDateTime = currentDateTime.format(formatter);
        // Create an instance of the Main class and run the menu
        new Main().runMenu();
    }

    public void runMenu(){
        String choice;
        // Set the flag to true to continue program execution
        runProgramme = true;

        do {
            // Print the menu options
            printMenu();
            // Get the user's choice
            choice = getValidInput("Enter your choice");

            // Perform actions based on the user's choice
            switch (choice) {
                case "100", "VFQ" -> viewAllQueues();
                case "101", "VEQ" -> viewAllEmptyQueues();
                case "102", "ACQ" -> addCustomerToQueue();
                case "103", "RCQ" -> removeCustomerFromQueue();
                case "104", "PCQ" -> chooseQueueToRemoveServedCustomer();
                case "105", "VCS" -> viewCustomerSorted();
                case "106", "SPD" -> storeProgramData();
                case "107", "LPD" -> loadProgramData();
                case "108", "STK" -> viewRemainingBurgers();
                case "109", "AFS" -> addBurgers();
                case "999", "EXT" -> exitProgram();
                default -> System.out.println("""
                        **********************************************
                        Invalid choice. Please try again.
                        **********************************************""");
            }

        } while (runProgramme);
    }

    public void printMenu() {
        // Check if the burger count is sufficient
        checkSufficientBurgerCount(10);
        // Print the menu options
        System.out.println("\nMenu Options:");
        System.out.println("\n100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue.");
        System.out.println("103 or RCQ: Remove a customer from a Queue.");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("106 or SPD: Store Program Data into file.");
        System.out.println("107 or LPD: Load Program Data from file.");
        System.out.println("108 or STK: View Remaining burgers Stock.");
        System.out.println("109 or AFS: Add burgers to Stock.");
        System.out.println("999 or EXT: Exit the Program.");
        System.out.println("--------------------------------------------------");
    }

    public void checkSufficientBurgerCount( int MinimumRequiredAmount){
        if (BurgerStockCount <= MinimumRequiredAmount){
            System.out.println("\n*****************************************************************************************************\n" +
                    "You have reached the minimum required amount of " + MinimumRequiredAmount +
                    " burgers in stock!!Adding more burgers is recommended before continuing!!" +
                    "\n*****************************************************************************************************");
        }
    }

    public void viewAllQueues() {
        System.out.println("\n*****************");
        System.out.println("*    Cashiers   *");
        System.out.println("*****************");

        for (int i = 0; i < queue3.length; i++) {
            System.out.print("   ");
            try {
                System.out.print(printArrayElementStatus(queue1, i));
            } catch (Exception IndexOutOfBoundsException) {
                System.out.print(" ");
            }
            System.out.print("    ");
            try {
                System.out.print(printArrayElementStatus(queue2, i));
            } catch (Exception IndexOutOfBoundsException) {
                System.out.print(" ");
            }
            System.out.print("    ");
            System.out.println(printArrayElementStatus(queue3, i));
        }
    }

    public String printArrayElementStatus(String[] array, int arrayIndex) {
        if (array[arrayIndex] == null) {
            return "X";
        } else {
            return "O";
        }
    }

    public void viewAllEmptyQueues() {
        System.out.println("\n**********************************************");
        if (checkForEmptySlot(queue1)){
            System.out.println("First Queue has empty slots");
        }
        if (checkForEmptySlot(queue2)){
            System.out.println("Second Queue has empty slots");
        }
        if (checkForEmptySlot(queue3)){
            System.out.println("Third Queue has empty slots");
        }
        if (!checkForEmptySlot(queue1) && !checkForEmptySlot(queue2) && !checkForEmptySlot(queue3)){
            System.out.println("None of the Queues have empty slots");
        }
        System.out.println("\n**********************************************");
    }

    public boolean checkForEmptySlot(String[] queue){
        for (String element : queue){
            if (element == null) {
                return true;
            }
        }
        return false;
    }

    public void addCustomerToQueue() {
        String queueNumber = getValidInput("Enter 1 , 2 or 3 to select a queue");
        switch (queueNumber) {
            case ("1") -> checkQueueAvailability(queue1);
            case ("2") -> checkQueueAvailability(queue2);
            case ("3") -> checkQueueAvailability(queue3);
            default -> {
                System.out.println("""
                        **********************************************
                        Invalid input! Please enter a valid queue number.
                        **********************************************""");
                addCustomerToQueue();
            }
        }
    }
    public void modifyQueue(String[] queue){
        String customerName = getValidInput("Enter customer name");
        for (int i = 0; i < queue.length; i++){
            if (queue[i] == null) {
                queue[i] = customerName;
                break;
            }
        }
    }

    public void checkQueueAvailability(String[] queue){
        if (queue[queue.length - 1] != null) {
            String choice = getValidInput("This queue is full! Put 'yes' to try another queue. Put 'no' to go back to menu");
            if (choice.equals("yes")) {
                addCustomerToQueue();
            }
            runMenu();
        } else {
            modifyQueue(queue);
        }
    }

    public void removeCustomerFromQueue(){
        String choice = getValidInput("Do you want to remove a customer? (yes/no)");
        if (choice.equals("yes")) {
            chooseQueueToRemoveCustomer();
        }
        runMenu();
    }

    public void chooseQueueToRemoveCustomer(){
        String choice = getValidInput("Choose the queue to remove customer from");
        switch (choice) {
            case ("1") -> removeCustomerInGivenIndex(queue1);
            case ("2") -> removeCustomerInGivenIndex(queue2);
            case ("3") -> removeCustomerInGivenIndex(queue3);
            default -> {
                System.out.println("""
                        **********************************************
                        Invalid input! Please enter a valid queue number.
                        **********************************************""");
                chooseQueueToRemoveCustomer();
            }
        }
    }
    public void removeCustomerInGivenIndex(String[] queue){
        int index = Integer.parseInt(getValidInput("Choose the index of the customer you wish to remove"));
        try {
            if(queue[index] == null){
                String choice = getValidInput("This slot is already empty!Input 'yes' if you wish " +
                        "to select a different slot, 'no' to go back to menu");
                if (choice.equals("yes")) {
                    removeCustomerInGivenIndex(queue);
                }
                runMenu();

            } else {
                String selectedCustomer = queue[index];
                String choice = getValidInput("Input 'yes' if you want to remove " + selectedCustomer +
                        " 'no' to go back to menu");
                if (choice.equals("yes")) {
                    queue[index] = queue[index + 1];
                    queue[index + 1] = null;
                    System.out.println(selectedCustomer + " was removed from queue.");
                    moveCustomerUpOneSlot(queue);
                }
                runMenu();
            }
        } catch (Exception IndexOutOfBoundsException) {
            String choice = getValidInput("Given index does not exist in this queue! " +
                    "Input 'yes' if you wish to select a different index. 'no' to go back to menu");
            if (choice.equals("yes")) {
                removeCustomerInGivenIndex(queue);
            }
            runMenu();
        }
    }

    public void moveCustomerUpOneSlot(String[] queue){
        for (int i = 0; i < queue.length ; i++){
            try {
                if (queue[i] == null) {
                    queue[i] = queue[i + 1];
                    queue[i + 1] = null;
                }
            } catch (Exception IndexOutOfBoundsException){
                break;
            }
        }
    }


    public void chooseQueueToRemoveServedCustomer(){
        String choice = getValidInput("Choose the queue to remove served customer from");
        switch (choice) {
            case ("1") -> removeServedCustomer(queue1, "Queue One");
            case ("2") -> removeServedCustomer(queue2, "Queue Two");
            case ("3") -> removeServedCustomer(queue3, "Queue Three");
            default -> {
                System.out.println("""
                        **********************************************
                        Invalid input! Please enter a valid queue number.
                        **********************************************""");
                chooseQueueToRemoveServedCustomer();
            }
        }
    }

    public void removeServedCustomer(String[] queue, String queueName) {
        String customerName = queue[0];
        if (queue[0] == null) {
            String choice = getValidInput("This Queue is empty! Choose a different queue? (yes/no)");
            if (choice.equals("yes")) {
                chooseQueueToRemoveServedCustomer();
            }
            runMenu();
        } else {
            queue[0] = null;
            System.out.println("\n**********************************************\n" + customerName +
                    " was served 5 burgers and removed from " + queueName + "!\n**********************************************");
            BurgerStockCount -= 5;
            totalBurgersSold += 5;
            addServedCustomerData(queueName, customerName);
            moveCustomerUpOneSlot(queue);
        }

    }

    public void addServedCustomerData(String queueName, String customerName){
        for (int i = 0; i < servedCustomerData.length; i++){
            if (servedCustomerData[i][0] == null){
                servedCustomerData[i][0] = customerName;
                servedCustomerData[i][1] = 5;
                servedCustomerData[i][2] = queueName;
                servedCustomerData[i][3] = LocalDateTime.now();
                break;
            }
        }
    }

    public void viewCustomerSorted(){
        String choice = getValidInput("Select queue to sort");
        switch (choice) {
            case ("1") -> System.out.println(Arrays.toString(sortCustomers(queue1)));
            case ("2") -> System.out.println(Arrays.toString(sortCustomers(queue2)));
            case ("3") -> System.out.println(Arrays.toString(sortCustomers(queue3)));
            default -> {
                System.out.println("""
                        **********************************************
                        Invalid input! Please enter a valid queue number.
                        **********************************************""");
                viewCustomerSorted();
            }
        }

    }

    public static String[] sortCustomers(String[] queue) {
        int n = queue.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (compareStrings(queue[j], queue[j+1]) > 0) {
                    String temp = queue[j];
                    queue[j] = queue[j+1];
                    queue[j+1] = temp;
                }
            }
        }
        return queue;
    }

    public static int compareStrings(String string1, String string2) {
        if (string1 == null && string2 == null) {
            return 0;
        } else if (string1 == null) {
            return 1;
        } else if (string2 == null) {
            return -1;
        } else {
            int minLength = Math.min(string1.length(), string2.length());
            for (int i = 0; i < minLength; i++) {
                char c1 = string1.charAt(i);
                char c2 = string2.charAt(i);
                if (c1 != c2) {
                    return c1 - c2;
                }
            }
            return string1.length() - string2.length();
        }
    }

    public void storeProgramData(){
        try {
            FileWriter myWriter = new FileWriter("Programme-log.txt", true);
            String logEntry = "Session started at: " + sessionStartDateTime + "\n-----------------------------------------\n" + loopAndPrintServedCustomers()
                    + "\nTotal Number of Burgers sold: " + totalBurgersSold + "\n-----------------------------------------\n";
            myWriter.write(logEntry);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
        }

    }

    public String loopAndPrintServedCustomers(){
        StringBuilder sb = new StringBuilder();

        for (Object[] servedCustomerDatum : servedCustomerData) {
            if (servedCustomerDatum[0] != null) {
                String customerName = servedCustomerDatum[0].toString();
                String burgerAmount = servedCustomerDatum[1].toString();
                String queueName = servedCustomerDatum[2].toString();
                String dateTime = servedCustomerDatum[3].toString();

                sb.append("\n")
                        .append(customerName)
                        .append(" was served ")
                        .append(burgerAmount)
                        .append(" burgers from ")
                        .append(queueName)
                        .append(" on ")
                        .append(dateTime)
                        .append("\n");
            }
        }
        return sb.toString();
    }

    public void loadProgramData(){
        try {
            FileReader fileReader = new FileReader("Programme-log.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }
    public void viewRemainingBurgers() {
        System.out.print("\n**********************************************\nNumber of Burgers remaining in stock: " + BurgerStockCount +
                "\n**********************************************" );
    }



    public void addBurgers(){
        try {
            int  burgersToAdd = Integer.parseInt(getValidInput("How many burgers do you want to add"));
            if (BurgerStockCount + burgersToAdd > 50){
                String choice = getValidInput("You can only add unto " + (50 - BurgerStockCount) +
                        " burgers! Input 'yes' to put a different amount, 'No' to go back to the menu");
                if (choice.equals("yes")) {
                    addBurgers();
                }
                runMenu();
            } else {
                BurgerStockCount += burgersToAdd;
                System.out.println(burgersToAdd + " number of burgers were added to the stock");
            }
        } catch (Exception e){
            System.out.println("""
                    ********************************
                    INVALID INPUT!TRY AGAIN
                    ********************************""");
            addBurgers();
        }

    }
    public String getValidInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.print("\n**********************************************\n" + prompt + " : ");
            input = scanner.nextLine().strip();

            if (!input.matches("[a-zA-Z0-9\\s]+")) {
                System.out.println("""
                        **********************************************
                        Invalid Input! Please enter a valid input.
                        **********************************************""");
            }
        } while (!input.matches("[a-zA-Z0-9\\s]+"));

        return input;
    }

    public void exitProgram(){
        String choice = getValidInput("INPUT 'yes' IF YOU WANT TO STORE THE DATA OF THIS SESSION BEFORE EXITING THE PROGRAMME," +
                " 'no' TO EXIT WITHOUT STORING DATA AND 'back' TO GO BACK TO MENU!!");
        switch (choice){
            case("yes"):
                storeProgramData();
                break;
            case("no"):
                runProgramme = false;
            case("back"):
                runMenu();
            default:
                System.out.println("""
                            **********************************************
                            Invalid choice. Please try again.
                            **********************************************""");
                break;

        }

    }

}