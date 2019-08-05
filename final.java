//Aiyanah "Kaiy" Muhammad
//July 7 2019
//Final Project: Library checkout system
//Eclipse IDE

package project;
import java.util.Arrays;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Queue;

public class Final {

    //Method to check out each and keep track of available books
    public static void checkOut(int[] releaseYear, String[] title) {

        //The value of this variable is used to determine if the patron is done checking out books
    	int again;
    	//The value of this variable is searched for in the releaseYear array during the binary search
        int target;
        //The value of this variable is used to process arrays
        int index;
        //The value of this variable all the books currently available in the system
        String availableBooks;
        //This variable stores the string that the user last input
        String input;
        //This object is needed to use the BinarySearch class
        BinarySearch ob;

        //Welcome
        JOptionPane.showMessageDialog(null, "Welcome to the Catonsville Library.");

        do {
            //Sorting method on releaseYear array for binary search
            Arrays.sort(releaseYear);

            //User inputs integer of release year they want
            JOptionPane.showMessageDialog(null, "Enter the release year of the book you would like to check out");
            availableBooks = title[0] + " year: " + releaseYear[0] + " || " + title[1] + " year: " + releaseYear[1] + " || " + title[2] +
                " year: " + releaseYear[2] + " || " + title[3] + " year: " + releaseYear[3] + " || " + title[4] + " year: " + releaseYear[4];
            input = JOptionPane.showInputDialog(availableBooks);
            target = Integer.parseInt(input);

            //Binary search
            ob = new BinarySearch();
            index = ob.binarySearch(releaseYear, 0, 4, target);

            if (index != -1 && !title[index].endsWith(" (unavailable)")){
                JOptionPane.showMessageDialog(null, "Thanks for checking out " + title[index] + ".");
                title[index] = title[index] + " (unavailable)";

                //this variable stores the string that the user last input
                String userInput = JOptionPane.showInputDialog("Do you want 2, 3, or 4 weeks with this book?");
                //This variable stores the integer that is the parsed user input
                int numWeeks = Integer.parseInt(userInput);
                dueMethod(numWeeks);

            } else {
                JOptionPane.showMessageDialog(null, "Sorry, we don't have that book.");
            }

            //Ask user if they would like to check out another book	
            again = JOptionPane.showConfirmDialog(null, "Would you like to check out another book?");

        }
        while (again == JOptionPane.YES_OPTION);

        //If user doesn't say yes, the program leaves this method
    }

    //Method to call Due subclasses
    public static void dueMethod(int weeks) {

        //Calls class with that matches input of desired weeks
        if (weeks == 2) {
            Due date = new DueIn2();
            date.getDate();
        } 
        
        else if (weeks == 3) {
            Due date = new DueIn3();
            date.getDate();
        } 
        
        else if (weeks == 4) {
            Due date = new DueIn4();
            date.getDate();
        }
        
        //If user input is not 2, 3, or 4, the method recursively call itself for new input
        else {
            String userInput = JOptionPane.showInputDialog("2, 3, or 4 week only");
            int numWeeks = Integer.parseInt(userInput);
            dueMethod(numWeeks);
        }
    }

    //Main driver method
    public static void main(String[] args) {

        //Creating array of book release years and another of their titles.
        int[] releaseYear = {
            1997,
            2014,
            2005,
            2017,
            1969
        };
        String[] title = {
            "Slaughterhouse-Five",
            "Harry Potter",
            "Percy Jackson",
            "Mr Mercedes",
            "The Hate U Give"
        };
        
        JOptionPane.showMessageDialog(null, "This is a library checkout. There are currently 3 patrons in the queue.");
        
        //Creates queue of people in line for checkout
        Queue < String > waitingQueue = new LinkedList < > ();
        waitingQueue.add("Patron — Anya");
        waitingQueue.add("Patron — Muhammad");
        waitingQueue.add("Patron — Arjun");
        JOptionPane.showMessageDialog(null, waitingQueue);

        //Each patron is checked out then removed from the queue
        for (int i=1; i<=3; i++) {
            //
        	String head = waitingQueue.peek(); 
            JOptionPane.showMessageDialog(null, head);
            checkOut(releaseYear, title);
            String removeHead = waitingQueue.remove(); 
            JOptionPane.showMessageDialog(null, removeHead +", thank you for supporting your local library" );
        }
        JOptionPane.showMessageDialog(null, "The library is closed.");
    }
}

//Binary search of for 'target' in array
class BinarySearch {
    int binarySearch(int array[], int l, int r, int target) {
        if (r >= l) {
        	
        	//the value of this variable is the middle element of the array
            int mid = l + (r - l) / 2;

            if (array[mid] == target) {
                return mid;
            }
            //recursive call
            if (array[mid] > target) {
                return binarySearch(array, l, mid - 1, target);
            }
            return binarySearch(array, mid + 1, r, target);
        }
        //if target is not in array
        return -1;
    }
}

//Parent Due class
class Due {
    //
	public int weeks;
    public void getDate() {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plus(weeks, ChronoUnit.WEEKS);
        JOptionPane.showMessageDialog(null, "Checked out: " + today + ". Due: " + dueDate);
    }
}

//Child Due class to getDate of 2 week book borrowing
class DueIn2 extends Due {
    {
        weeks = 2;
    }
}

//Child Due class to getDate of 3 week book borrowing
class DueIn3 extends Due {
    {
        weeks = 3;
    }
}

//Child Due class to getDate of 4 week book borrowing
class DueIn4 extends Due {
    {
        weeks = 4;
    }
}
