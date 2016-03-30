import java.util.*;
import com.sleepycat.db.*;
import java.io.*;

                

public class Main{
  
//login replaced with database setup
public static void setup(String type){
  if(type.equalsIgnoreCase("BTREE")){
    Globals.dbConfig.setType(DatabaseType.BTREE);
    Globals.dbConfig.setAllowCreate(true);
    
    System.out.println("Database setup is: btree.");
  }
  else if(type.equalsIgnoreCase("HASH")){
    Globals.dbConfig.setType(DatabaseType.HASH);
    Globals.dbConfig.setAllowCreate(true);
    
    System.out.println("Database setup is: hash.");
  }
  else if(type.equalsIgnoreCase("INDEXFILE")){
    //something something
    System.out.println("Indexfile coming soon!");
  }
  
  try{
    Globals.my_table = new Database(Globals.location, null, Globals.dbConfig);
  }catch(Exception eep){
    System.err.println("Could not create the database:" + eep.toString());
  }
  
}


public static void menu(){
  //main menu for navigation

  AddData add = new AddData();
  
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

        add.getEntry();
        
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
      System.out.println(args.length);
      if(args.length == 1){

        //takes: btree, hash, indexfile
        if(args[0].equalsIgnoreCase("BTREE") || args[0].equalsIgnoreCase("HASH") || args[0].equalsIgnoreCase("INDEXFILE")){

          setup(args[0]);
          
        }else{
          System.out.println("Not the right input. Require: Btree, Hash, IndexFile");
          System.exit(0);
        }
        
      }else{
        System.out.println("One argument required:\n" +
                           "BTree\n" +
                           "Hash\n" +
                           "IndexFile");
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
