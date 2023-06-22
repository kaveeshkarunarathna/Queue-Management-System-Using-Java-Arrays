import java.util.Arrays;
import java.util.Scanner;

public class Main {
    int burgerCount = 50;
    String[] queue1 = new String[2];
    String[] queue2 = new String[3];
    String[] queue3 = new String[5];


    public static void main(String[] args) {
        new Main().runMenu();

    }

    private void runMenu(){
        String choice;

        do {
            printMenu();
            choice = getValidInput("Enter your choice");

            switch (choice) {
                case "100":
                case "VFQ":
                    viewAllQueues();
                    break;
                case "101":
                case "VEQ":
                    viewAllEmptyQueues();
                    break;
                case "102":
                case "ACQ":
                    addCustomerToQueue();
                    break;
                case "103":
                case "RCQ":
                    removeCustomerFromQueue();
                    break;
                case "104":
                case "PCQ":
                    chooseQueueToRemoveServedCustomer();
                    break;
                case "105":
                case "VCS":
                    viewCustomerSorted();
                    break;
                case "106":
                case "SPD":
                    storeProgramData();
                    break;
                case "107":
                case "LPD":
                    loadProgramData();
                    break;
                case "108":
                case "STK":
                    viewRemainingBurgers();
                    break;
                case "109":
                case "AFS":
                    addBurgers();
                    break;
                case "999":
                case "EXT":
                    exitProgram();
                    break;
                default:
                    System.out.println("""
                            **********************************************
                            Invalid choice. Please try again.
                            **********************************************""");
                    break;
            }

        } while (!choice.equals("999") && !choice.equals("EXT"));
    }

    private void printMenu() {
        checkSufficientBurgerCount(10);
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

    private void viewAllQueues() {
        System.out.println("\n*****************");
        System.out.println("*    Cashiers   *");
        System.out.println("*****************");

        for (int i = 0; i < queue3.length; i++) {
            System.out.print("   ");
            try {
                System.out.print(printArrayElementStatus(queue1, i));
            } catch (Exception e) {
                System.out.print(" ");
            }
            System.out.print("    ");
            try {
                System.out.print(printArrayElementStatus(queue2, i));
            } catch (Exception e) {
                System.out.print(" ");
            }
            System.out.print("    ");
            System.out.println(printArrayElementStatus(queue3, i));
        }

        System.out.println(Arrays.toString(queue1));
        System.out.println(Arrays.toString(queue2));
        System.out.println(Arrays.toString(queue3));
    }

    private String printArrayElementStatus(String[] array, int arrayIndex) {
        if (array[arrayIndex] == null) {
            return "X";
        } else {
            return "O";
        }
    }

    private void viewAllEmptyQueues() {
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

    private void addCustomerToQueue() {
        String queueNumber = getValidInput("Enter 1 , 2 or 3 to select a queue");
        switch (queueNumber){
            case("1"):
                checkQueueAvailability(queue1);
                break;
            case("2"):
                checkQueueAvailability(queue2);
                break;
            case("3"):
                checkQueueAvailability(queue3);
                break;
            default:
                System.out.println("""
                        **********************************************
                        Invalid input! Please enter a valid queue number.
                        **********************************************""");
                addCustomerToQueue();
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
            switch (choice){
                case("yes"):
                    addCustomerToQueue();
                default:
                    runMenu();
            }
        } else {
            modifyQueue(queue);
        }
    }

    private void removeCustomerFromQueue(){
        String choice = getValidInput("Do you want to remove a customer? (yes/no)");
        switch (choice){
            case ("yes"):
                chooseQueueToRemoveCustomer();
            default:
                runMenu();
        }
    }

    private void chooseQueueToRemoveCustomer(){
        String choice = getValidInput("Choose the queue to remove customer from");
        switch(choice){
            case("1"):
                removeCustomerInGivenIndex(queue1);
                break;
            case("2"):
                removeCustomerInGivenIndex(queue2);
                break;
            case("3"):
                removeCustomerInGivenIndex(queue3);
                break;
            default:
                System.out.println("""
                        **********************************************
                        Invalid input! Please enter a valid queue number.
                        **********************************************""");
                chooseQueueToRemoveCustomer();
        }
    }
    public void removeCustomerInGivenIndex(String[] queue){
        int index = Integer.parseInt(getValidInput("Choose the index of the customer you wish to remove"));
        try {
            if(queue[index] == null){
                String choice = getValidInput("This slot is already empty!Input 'yes' if you wish to select a different slot, 'no' to go back to menu");
                switch (choice){
                    case("yes"):
                        removeCustomerInGivenIndex(queue);
                    default:
                        runMenu();
                }

            } else {
                String selectedCustomer = queue[index];
                String choice = getValidInput("Input 'yes' if you want to remove " + selectedCustomer + " 'no' to go back to menu");
                switch (choice){
                    case("yes"):
                        queue[index] = queue[index + 1];
                        queue[index + 1] = null;
                        System.out.println(selectedCustomer + " was removed from queue.");
                        moveCustomerUpOneSlot(queue);
                    default:
                        runMenu();
                }
            }
        } catch (Exception IndexOutOfBoundsException) {
            String choice = getValidInput("Given index does not exist in this queue! Input 'yes' if you wish to select a different index. 'no' to go back to menu");
            switch (choice){
                case("yes"):
                    removeCustomerInGivenIndex(queue);
                default:
                    runMenu();
            }
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

    private void chooseQueueToRemoveServedCustomer(){
        String choice = getValidInput("Choose the queue to remove served customer from");
        switch(choice){
            case("1"):
                removeServedCustomer(queue1);
                break;
            case("2"):
                removeServedCustomer(queue2);
                break;
            case("3"):
                removeServedCustomer(queue3);
                break;
            default:
                System.out.println("""
                        **********************************************
                        Invalid input! Please enter a valid queue number.
                        **********************************************""");
                chooseQueueToRemoveServedCustomer();
        }
    }

    public void removeServedCustomer(String[] queue) {
        String customerName = queue[0];
        if (queue[0] == null) {
            String choice = getValidInput("This Queue is empty! Choose a different queue? (yes/no)");
            switch (choice){
                case("yes"):
                    chooseQueueToRemoveServedCustomer();
                default:
                    runMenu();
            }
        } else {
            queue[0] = null;
            System.out.println("\n**********************************************\n" + customerName +
                    " was served 5 burgers and removed from queue!\n**********************************************");
            burgerCount -= 5;
            moveCustomerUpOneSlot(queue);
        }

    }

    private void viewCustomerSorted(){
        String choice = getValidInput("Select queue to sort");
        switch (choice) {
            case("1"):
                System.out.println(Arrays.toString(sortCustomers(queue1)));
                break;
            case ("2"):
                System.out.println(Arrays.toString(sortCustomers(queue2)));
                break;
            case("3"):
                System.out.println(Arrays.toString(sortCustomers(queue3)));
                break;
            default:
                System.out.println("""
                        **********************************************
                        Invalid input! Please enter a valid queue number.
                        **********************************************""");
                viewCustomerSorted();
        }

    }

    public static String[] sortCustomers(String[] queue) {
        int n = queue.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (compareStrings(queue[j], queue[j+1]) > 0) {
                    String temp = queue[j];
                    queue[j] = queue[j+1]  = temp;
                }
            }
        }
        return queue;
    }

    public static int compareStrings(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return 0;
        } else if (s1 == null) {
            return 1;
        } else if (s2 == null) {
            return -1;
        } else {
            int minLength = Math.min(s1.length(), s2.length());
            for (int i = 0; i < minLength; i++) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);
                if (c1 != c2) {
                    return c1 - c2;
                }
            }
            return s1.length() - s2.length();
        }
    }


    private void storeProgramData(){

    }
    private void loadProgramData(){

    }
    private void viewRemainingBurgers() {
        System.out.print("\n**********************************************\nNumber of Burgers remaining in stock: " + burgerCount +
                "\n**********************************************" );
    }

    public void checkSufficientBurgerCount( int MinimumRequiredAmount){
        if (burgerCount <= MinimumRequiredAmount){
            System.out.println("\n*****************************************************************************************************\n" +
                    "You have reached the minimum required amount of " + MinimumRequiredAmount +
                                " burgers in stock!!Adding more burgers is recommended before continuing!!" +
                    "\n*****************************************************************************************************");
        }
    }

    private void addBurgers(){
        try {
            int  burgersToAdd = Integer.parseInt(getValidInput("How many burgers do you want to add"));
            if (burgerCount + burgersToAdd > 50){
                String choice = getValidInput("You can only add upto " + (50 - burgerCount) +
                        " burgers! Input 'yes' to put a different amount, 'No' to go back to the menu");
                switch(choice) {
                    case ("yes"):
                        addBurgers();
                    default:
                        runMenu();
                }
            } else {
                burgerCount += burgersToAdd;
                System.out.println(burgersToAdd + " number of burgers were added to the stock");
            }
        } catch (Exception e){
            System.out.println("\n********************************\nINVALID INPUT!TRY AGAIN" +
                    "\n********************************");
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
                System.out.println("\n**********************************************\n" +
                        "Invalid Input! Please enter a valid input.\n**********************************************");
            }
        } while (!input.matches("[a-zA-Z0-9\\s]+"));

        return input;
    }

    private void exitProgram(){

    }

}