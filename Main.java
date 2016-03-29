import java.util.*;
import com.sleepycat.db.*; //for Berkeley DB stuff
import java.io.*;

                

public class Main{
  
//login removed, don't think there is a login aspect for this part


public static void menu(){
  //main menu for navigation

  
  System.out.println("-----------------------------------------------------");
  
  System.out.println("Welcome to the Database System!\n Please select an option to begin:");

  System.out.println("1- Create and populate a database\n2- Retrieve records with a given key\n3- Retrieve records with a given data\n4- Retrieve records with a given range of key values\n5- Destroy the database (!!!)\n6- Quit");

    System.out.println("-----------------------------------------------------");


    //listen for input
    Scanner scanner = IO.getScanner();
    String input = scanner.nextLine();

    try{
      int number = Integer.parseInt(input);

      switch(number){
      case 1:
        System.out.println("Welcome to The Database Populating System!");
        
        
        //returning from this - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 2:
        System.out.println("Welcome to Record Retrieval with Your Given Key!");
        
        
        //returning from this - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 3:
        System.out.println("Welcome to Record Retrieval with Your Given Data!");
        
        //returning from this - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 4:
        System.out.println("Welcome to Record Retrieval with a Given Range of Key Values!");
	
        //returning from this - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 5:
        //they probably want this to happen right away since it's being done by a program
        System.out.println("Destroying the database (!!!!!!!!)");
        
        //returning from search - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 6:
        System.out.println("Bye-bye!");
        System.exit(0);
        break;
        
      default:
        System.out.println("That is not a valid input! Try again.");
        menu(); //iffy but fix later if problems
        break;

      }
    }catch(NumberFormatException e){
      System.out.println("That is not a valid input! Try again.");
      menu(); //this is kinda weird but works for now
    }  

}

    public static void main(String[] args){

      //get the args, make sure there are args
      if(args.length == 1){

        try{
          int value = Integer.parseInt(args[0]); //assign this to a global later probably
        }catch(NumberFormatException e){
          System.err.println("Input must be a number: 1 2 3");
          System.exit(1);
        }
        
      }else{
        System.out.println("One argument required:\n" +
                           "'1' - BTree\n" +
                           "'2' - Hash\n" +
                           "'3' - IndexFile");
        System.exit(0);
      }
        
        
        try{
            // Initialize "global" scanner
            //Scanner scanner = IO.getScanner("test.txt");
            menu();
        }catch(NoSuchElementException e){
          //System.out.println("End of tests, returning to stdin.");

          //Scanner scanner = IO.resetScanner();
            menu();
        }
            
        System.out.println("Bye! :D");
  
 
    }//end of main

}
