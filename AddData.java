import java.util.*;
import com.sleepycat.db.*; //for Berkeley DB stuff
import java.io.*;

public class AddData{
  
  //rusn the sample code to create the data
  //not offering a lot of options here that make it wait because
  //it is being marked with a program so dont wanna confuse it

  public void populateTable(){
    int range;
    DatabaseEntry kdbt, ddbt;
    DatabaseEntry empty = new DatabaseEntry();
    String s;
    int count=0;
    /*
     *  generate a random string with the length between 64 and 127,
     *  inclusive.
     *
     *  Seed the random number once and once only.
     */
    Random random = new Random(1000000);
    OperationStatus opSts;

    // KG: Two strings to hold key data pairs, which are printed for testing
    String s1;
    String s2;
    
    try {
      Cursor cursor = Globals.my_table.openCursor(null, null);
      
      for (int i = 0; i < 15; i++) { //**100000

        do{
        
        /* to generate a key string */
        range = 64 + random.nextInt( 64 );
        s = "";
        for ( int j = 0; j < range; j++ )
          s+=(new Character((char)(97+random.nextInt(26)))).toString();

        // KG: key
        s1 = s;
        
        /* to create a DBT for key */
        kdbt = new DatabaseEntry(s.getBytes());
        kdbt.setSize(s.length());
        
        // to print out the key/data pair
        // System.out.println(s);

        /* to generate a data string */
        range = 64 + random.nextInt( 64 );
        s = "";
        for ( int j = 0; j < range; j++ )
          s+=(new Character((char)(97+random.nextInt(26)))).toString();
        // to print out the key/data pair
        //System.out.println(s);
        //System.out.println("");

        // KG: data
        s2 = s;
        
        /* to create a DBT for data */
        ddbt = new DatabaseEntry(s.getBytes());
        ddbt.setSize(s.length());


        //check if the key is already in the database!
        //assignment says not to reuse a rejected key/data pair, so im NOT
        //whatever data the key it matched with will also be rejected
        opSts = cursor.getSearchKey(kdbt, empty, null);
        //System.out.println("opSts = " + opSts);
        
        }while(opSts == OperationStatus.SUCCESS);
        
        /* to insert the key/data pair into the database */
        Globals.my_table.putNoOverwrite(null, kdbt, ddbt);
        count++;
        if (true)System.out.println("Key: " + s1 + "\nData: " + s2); //count%20000 == 0
        
      }
      System.out.println("Table successfully populated!");
      System.out.println("Count's value is: " + count);
      cursor.close();
    }
    catch (DatabaseException dbe) {
      System.err.println("Populate the table: "+dbe.toString());
      System.exit(1);
    }
    
  }

  //destroys only the table, NOT the file location
  //the file location is destroyed when the user exits the program completely
  public void destroyTable(){
    try{
      System.out.println("Destroying the database...");
      Globals.my_table.close();
      Globals.my_table.remove(Globals.location + "/" + Globals.db_filename,
                              null,
                              null); 
      System.out.println("Database successfully destroyed!");
    }catch(Exception e){
      System.err.println("Problem deleting the table: " + e);
    }
    
  }

}
