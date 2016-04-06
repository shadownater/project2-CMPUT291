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
        
    OperationStatus opSts;
    String dataResult, keyResult;
    int count=0;
    beforeTime = System.nanoTime();
    
    try{

      Cursor cursor = Globals.my_table.openCursor(null, null);
      
      //sooo... gotta check EVERY SINGLE ENTRY to see if its data is the specified data
      //record the keys that match in an arraylist of DatabaseEntry
      
      for(int i=0; i < 100000; i++){ 
        
        DatabaseEntry dataValue = new DatabaseEntry();
        DatabaseEntry keys = new DatabaseEntry();
        opSts = cursor.getNext(keys, dataValue, null);
        
        //if success - found something
        if(opSts == OperationStatus.SUCCESS){
          
          //compare against the data you have and the key's
          dataResult = new String(dataValue.getData()); 
          //dataResult = dataValue.getData().toString();
          
        
          keyResult = new String(keys.getData()); 
          
          //test code
          //System.out.println("Data: " + dataResult + "\n");
          
          if(input.equals(dataResult)){
            //found a match!
            System.out.println("" + cursor.count() + " key(s) successfully found.");
            System.out.println("Data is: " + dataResult + "\nKey result is: " + keyResult);     
            
            //add the key to the answers.txt
            
            try{
              
              PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Globals.answers.getName(), true)));
              
              
            //**important formatting part! makre sure it is this way!**
              
              String keyStuff = new String(keys.getData());
              String dataStuff = new String(dataValue.getData());
              
              //one line for the key string:
              out.println(keyStuff);
              
              //one line for the data string:
              out.println(dataStuff);
              
              //and one line for an empty string:
              out.println();
              
            out.close();
            
            //**end of cool formatting part**
            
            System.out.println("Results written to answers file.");
            
            }catch(IOException e){
              System.err.println("Printwriter error: " + e );
              System.exit(1);
            }
            
          }else{
            //didn't find a match for this time
          count++;
          }
          
        }
        
        
    }
      
      if(count==100000) System.out.println("No keys found for inputted data."); 
      
      //record the after time (I'm assuming we record it after we
      //have gone through the entire hash since that is the entire query

      afterTime = System.nanoTime();

      queryTime = afterTime - beforeTime;

      queryTime *= 0.001;
      System.out.println("Query time: " + queryTime + " microseconds");

      cursor.close();
      
     }catch(DatabaseException dbe){
      System.err.println("Search by data error: "+dbe.toString());
      System.exit(1);
    }
    
  }

//finds: low <= x <= upp
  public void findByRange(String lowInput, String upperInput){

    //compare each entry on if it is within the bounds of the upper and lower limits

    OperationStatus opSts;
    String dataResult, keyResult;
    int count=0;
    int total=0;
    beforeTime = System.nanoTime();
    
    try{

      Cursor cursor = Globals.my_table.openCursor(null, null);
      
      for(int i=0; i < 100000; i++){

        DatabaseEntry dataValue = new DatabaseEntry();
        DatabaseEntry keys = new DatabaseEntry();

        opSts = cursor.getNext(keys, dataValue, null);

        //if success - found something
        if(opSts == OperationStatus.SUCCESS){

          //compare against the data you have and the key's
          dataResult = new String(dataValue.getData());
          
          keyResult = new String(keys.getData());
          
          //compare the key, see if it is within the range

          //this seems to be broken
          if(keyResult.compareTo(lowInput) >= 0 && keyResult.compareTo(upperInput) <= 0){
            //in here if the string is valid!

            total++;

            //add the key to the answers.txt

            try{

              PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Globals.answers.getName(), true)));


              //**important formatting part! makre sure it is this way!**

              String keyStuff = new String(keys.getData());
              String dataStuff = new String(dataValue.getData());

              //one line for the key string:
              out.println(keyStuff);

              //one line for the data string:
              out.println(dataStuff);

              //and one line for an empty string:
              out.println();

              out.close();

              //**end of cool formatting part**

              
            }catch(IOException e){
              System.err.println("Printwriter error: " + e );
              System.exit(1);
            }
            
          }else{
            count++;
          }
    
        }

      }//end of loop

      if(count==100000) System.out.println("No records found for inputted range.");

      //record the after time (I'm assuming we record it after we
      //have gone through the entire hash since that is the entire query

      afterTime = System.nanoTime();

      queryTime = afterTime - beforeTime;

      queryTime *= 0.001;
      System.out.println("Query time: " + queryTime + " microseconds");

      System.out.println("" + total + " record(s) found within the range.\nResults written to answer file.");
      
      cursor.close();

    }catch(DatabaseException dbe){
      System.err.println("Search by data error: "+dbe.toString());
      System.exit(1);
    }
    
  }   

}
