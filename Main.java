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
      Globals.secDbConfig.setSortedDuplicates(true);
    
      Globals.secDbConfig.setAllowCreate(true);
      Globals.secDbConfig.setSortedDuplicates(true);
      Globals.secDbConfig.setKeyCreator(Globals.keyCreator);
      Globals.secDbConfig.setType(DatabaseType.BTREE);
      
      System.out.println("Database setup is: indexfile.");    
    }
  
    try{
      // Start by creating working directory
      DirectoryHelper.makeDirectory(Globals.location);

      //delete an existing answers file - only deletes on startup
      if(Globals.answers.exists()){
        Globals.answers.delete();
        Globals.answers.createNewFile();
      }else Globals.answers.createNewFile();
      
       
    
    }catch(Exception e){
      System.err.println("Could not create directory " + Globals.location +
                         ": " + e.toString());
    }
  
  }


  public static void menu(String value){
    //main menu for navigation


    HashFunctions hasher = new HashFunctions();
    IndexfileFunctions indexer = new IndexfileFunctions();
    BTreeFunctions climber = new BTreeFunctions();
    
    AddData add = new AddData();  


    //listen for input
    Scanner scanner = IO.getScanner();
    String input;
    int n = 0;

    // main loop, ie application engine
    while(true){
      if (n != -1) {
        System.out.println("-----------------------------------------------------"); 
        System.out.println("Welcome to the Database System!");
        System.out.println("Please select an option to begin:");
        System.out.println("1- Create and populate a database");
        System.out.println("2- Retrieve records with a given key");
        System.out.println("3- Retrieve records with a given data");
        System.out.println("4- Retrieve records with a given range of key values");
        System.out.println("5- Destroy the database (!!!)");
        System.out.println("6- Quit");                    
        System.out.println("-----------------------------------------------------");
      }


      // Get input
      input = scanner.nextLine();
      try {
        n = Integer.parseInt(input);
      } catch(Exception e){
        n = -1;
      }          

      // Case 1
      if (n == 1) {
        System.out.println("Populating the database... ");
        try {
          // Create database
          Globals.my_table = new Database(Globals.location + "/" + Globals.db_filename,
                                          null,
                                          Globals.dbConfig);

          // Create secondary database (secondary index) if applicable
          if(value.equalsIgnoreCase("INDEXFILE")){
            Globals.secDb = new SecondaryDatabase(Globals.location + "/" + Globals.sdb_filename,
                                                  null,
                                                  Globals.my_table,
                                                  Globals.secDbConfig);
          }
        } catch(Exception e){
          System.out.println("Could not create database: " + e);
        }

        // Populate Database
        add.populateTable();
        
        //returning from this - go back to the main menu
        System.out.println();
      }

      // Case 2
      else if (n == 2) {

          System.out.println("Welcome to Record Retrieval with Your Given Key!\n" +
                             "Please enter the key you wish to find: ");

        String keyInput = scanner.nextLine();

        //do the thing based on the chosen type on run:
        if(value.equalsIgnoreCase("BTREE")){
          climber.findByKeyB(keyInput);
          
        }
        else if(value.equalsIgnoreCase("HASH")){
          hasher.findByKey(keyInput);
          
          
        }
        else if(value.equalsIgnoreCase("INDEXFILE")){
          // btree or hash implement this optimally (KG: my guess btree)
          
        }
          
          //returning from this - go back to the main menu
    

        System.out.println();
      }

      // Case 3
      else if (n == 3) {

          System.out.println("Welcome to Record Retrieval with Given Data!\n" +
          "Please enter the data you wish to find: ");

        String dataInput = scanner.nextLine();

        if(value.equalsIgnoreCase("BTREE")){
          climber.findByDataB(dataInput);
        }
        else if(value.equalsIgnoreCase("HASH")){
          hasher.findByData(dataInput);
          
        }
        else if(value.equalsIgnoreCase("INDEXFILE")){
          // Note: indexer uses secDb, whose keys are data from my_table
          indexer.findByKey(dataInput);
        }

        
        //returning from this - go back to the main menu
        System.out.println();
      }
      // Case 4
      else if (n == 4) {

        System.out.println("Welcome to Record Retrieval with a Given Range of Key Values!\n" +
                           "Please enter the lower bound: ");

        String rangeInput1 = scanner.nextLine();

        System.out.println("Upper bound: ");

        String rangeInput2 = scanner.nextLine();
        
        if(value.equalsIgnoreCase("BTREE")){
          climber.findByRangeB(rangeInput1, rangeInput2);
        }
        else if(value.equalsIgnoreCase("HASH")){
          hasher.findByRange(rangeInput1, rangeInput2);
        }
        else if(value.equalsIgnoreCase("INDEXFILE")){
          // btree or hash implement this optimally (KG: my guess btree)

        }

	
      //returning from this - go back to the main menu
      System.out.println();
    }
      
    // Case 5
    else if (n == 5) {
      // KG: Just destroy the database, no need to do extra?
      // JL: they probably want this to happen right away
      //     since it's being done by a program
      add.destroyTable();
      // setup(input); <-- needed?
        
      //returning from search - go back to the main menu
      System.out.println();
    }
    // Case 6
    else if (n == 6) {
      System.out.println("Bye-bye!");
      DirectoryHelper.deleteDir(Globals.location);
      System.exit(0);
    }

    // Default
    else {
      System.out.println("That is not a valid input! Try again.");

      // set so menu is not 'redrawn'
      n = -1;
    }
  }
}

  public static void main(String[] args){
    //get the args, make sure there are args
    System.out.println(args.length);
    if(args.length == 1){

      //takes: btree, hash, indexfile
      if(args[0].equalsIgnoreCase("BTREE") ||
         args[0].equalsIgnoreCase("HASH") ||
         args[0].equalsIgnoreCase("INDEXFILE")){

        setup(args[0]);
          
      }else{
        System.out.println("Please specify: Btree, Hash, IndexFile");
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
