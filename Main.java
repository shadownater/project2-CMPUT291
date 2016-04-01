import java.util.*;
import com.sleepycat.db.*;
import java.io.*;
import java.io.File.*;
                

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
    Globals.dbConfig.setType(DatabaseType.BTREE);
    Globals.dbConfig.setAllowCreate(true);
    
    //needs something more to it than this
    System.out.println("Indexfile coming soon!");
  }
  
  try{
    Globals.my_table = new Database(Globals.location, null, Globals.dbConfig);
  }catch(Exception eep){
    System.err.println("Could not create the database:" + eep.toString());
  }
  
}


public static void menu(String value){
  //main menu for navigation

  AddData add = new AddData();
  HashFunctions hasher = new HashFunctions();
  
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
        System.out.println("Populating the database... ");

        add.populateTable();
        
        //returning from this - go back to the main menu
        System.out.println();
        menu(value);
        break;
        
      case 2:
        System.out.println("Welcome to Record Retrieval with Your Given Key!\n" +
          "Please enter the key you wish to find: ");

        String keyInput = scanner.nextLine();

        
        
        //do the thing based on the chosen type on run:
        if(value.equalsIgnoreCase("BTREE")){
          
          
        }
        else if(value.equalsIgnoreCase("HASH")){
          hasher.findByKey(keyInput);
          
          
        }
        else if(value.equalsIgnoreCase("INDEXFILE")){
          
        }
        

        //returning from this - go back to the main menu
        System.out.println();
        menu(value);
        break;
        
      case 3:
        System.out.println("Welcome to Record Retrieval with Your Given Data!\n" +
          "Please enter the data you wish to find: ");

        String dataInput = scanner.nextLine();

        if(value.equalsIgnoreCase("BTREE")){
          
        }
        else if(value.equalsIgnoreCase("HASH")){
          hasher.findByData(dataInput);
          
        }
        else if(value.equalsIgnoreCase("INDEXFILE")){

        }
        
        
        //returning from this - go back to the main menu
        System.out.println();
        menu(value);
        break;
        
      case 4:
        System.out.println("Welcome to Record Retrieval with a Given Range of Key Values!\n" +
                           "Please enter the lower bound: ");

        String rangeInput1 = scanner.nextLine();

        System.out.println("Upper bound: ");

        String rangeInput2 = scanner.nextLine();
        
        if(value.equalsIgnoreCase("BTREE")){
          
        }
        else if(value.equalsIgnoreCase("HASH")){
          hasher.findByRange(rangeInput1, rangeInput2);
        }
        else if(value.equalsIgnoreCase("INDEXFILE")){

        }
        
        
        //returning from this - go back to the main menu
        System.out.println();
        menu(value);
        break;
        
      case 5:
        //they probably want this to happen right away since it's being done by a program
        add.destroyTable();
        setup(input);
        
        //returning from search - go back to the main menu
        System.out.println();
        menu(value);
        break;
        
      case 6:
        System.out.println("Bye-bye!");
        add.destroyTable();
        Globals.file.delete();
        System.exit(0);
        break;
        
      default:
        System.out.println("That is not a valid input! Try again.");
        menu(value); //iffy but fix later if problems
        break;

      }
    }catch(NumberFormatException e){
      System.out.println("That is not a valid input! Try again.");
      menu(value); //this is kinda weird but works for now
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
            menu(args[0]);
        }catch(NoSuchElementException e){
          //System.out.println("End of tests, returning to stdin.");

          //Scanner scanner = IO.resetScanner();
            menu(args[0]);
        }
            
        System.out.println("Bye! :D");
  
 
    }//end of main

}
