import java.util.*;
import com.sleepycat.db.*;
import java.io.*;
import java.io.File.*;


//this class will handle the cases of using the hash
public class HashFunctions{

  long beforeTime, afterTime, queryTime;


  
//this function uses the hash table setup to search for a given key
  public void findByKey(String input){
    
    DatabaseEntry keyValue = new DatabaseEntry(input.getBytes());
    keyValue.setSize(input.length());
    
    DatabaseEntry data = new DatabaseEntry();
    
    try{

      Cursor cursor = Globals.my_table.openCursor(null, null);

      
      beforeTime = System.nanoTime();
      OperationStatus opSts = cursor.getSearchKey(keyValue, data, null);

      //if success - found something
      if(opSts == OperationStatus.SUCCESS){
        System.out.println("Key(s) successfully found. Found " + cursor.count() + " key(s).");
        
        //get time stamp
        afterTime = System.nanoTime();

        queryTime = afterTime - beforeTime;
        queryTime *= 0.001;
        System.out.println("Query time: " + queryTime + " microseconds");


        try{
        
        //write to the file - requires a writer - second parameter tells it to append!
          PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Globals.answers.getName(), true)));

          //**important formatting part! makre sure it is this way!**
          
          String keyStuff = new String(keyValue.getData());
          String dataStuff = new String(data.getData());
          
          //one line for the key string:
          out.println(keyStuff);
          
          //one line for the data string:
          out.println(dataStuff);
          
          //and one line for an empty string:
          out.println();
          
          out.close();

          //**end of cool formatting part**
          
          System.out.println("Results written to answers file.");
          
          cursor.close();
          
        }catch(IOException e){
          System.err.println("Printwriter error: " + e );
          System.exit(1);
        }
        
      //do this 4 times with different values each time!
      //then record the average

      }else{
      //if failure - didn't find anything
        System.out.println("Unable to find given key.");
      }
      
    }catch(DatabaseException dbe){
      System.err.println("Search by key error: "+dbe.toString());
      System.exit(1);
    }
    
  }



//this function finds the keys related to a given data input
  public void findByData(String input){


    DatabaseEntry dataValue = new DatabaseEntry();
    //dataValue.setSize(input.length());
    
    DatabaseEntry keys = new DatabaseEntry();
    OperationStatus opSts;
    //ArrayList<DatabaseEntry> myList = new ArrayList<DatabaseEntry>();

    String dataResult, keyResult;
    int count=0;
    
     try{

      Cursor cursor = Globals.my_table.openCursor(null, null);
    
    //sooo... gotta check EVERY SINGLE ENTRY to see if its data is the specified data
    //record the keys that match in an arraylist of DatabaseEntry

      for(int i=0; i < 15; i++){ //***100000

      opSts = cursor.getNext(keys, dataValue, null);

      //if success - found something
      if(opSts == OperationStatus.SUCCESS){

        //compare against the data you have and the key's
        dataResult = new String(dataValue.getData());

        keyResult = new String(keys.getData());

        //test code
        System.out.println("Data: " + dataResult + "\n");
        
        if(input.equals(dataResult)){
          //found a match!
          System.out.println("" + cursor.count() + " key(s) successfully found.");
          System.out.println("Data is: " + dataResult + "\nKey result is: " + keyResult);

          
        }else{
          //didn't find a match for this time
          count++;
        }

      }
     
      
    }

    if(count==100000) System.out.println("No keys found for inputted data.");

     }catch(DatabaseException dbe){
      System.err.println("Search by data error: "+dbe.toString());
      System.exit(1);
    }
    
  }

//finds: low <= x <= upp
  public void findByRange(String lowInput, String upperInput){
    System.out.println("Find by range haha!");
  }


}
