import java.util.*;
import com.sleepycat.db.*;
import java.io.*;
import java.io.File.*;

// This class will handle the cases of using btree
public class BTreeFunctions {
  // Use these to calculate query times
  long beforeTime, afterTime, queryTime;

  public void findByKeyB(String input){
    DatabaseEntry keyValue = new DatabaseEntry(input.getBytes());
    keyValue.setSize(input.length());

    DatabaseEntry data = new DatabaseEntry();

    try {
      // Create a cursor
      Cursor cursor = Globals.my_table.openCursor(null,null);

      // Start time
      beforeTime = System.nanoTime();

      // execute search
      OperationStatus oprStatus = cursor.getSearchKey(keyValue, data, null);

      // Handle Results:
      if ( oprStatus == OperationStatus.SUCCESS ) {
        // If we succeed...
        System.out.println("SUCCESS: " + new String(data.getData()));

        // Calculate query time
        afterTime = System.nanoTime();

        queryTime = afterTime - beforeTime;
        queryTime *= 0.001;
        System.out.println("Query time: " + queryTime + " microseconds");

        // Save the query time to a file
        try {
          PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Globals.answers.getName(), true)));
          
          String keyStuff = new String(keyValue.getData());
          String dataStuff = new String(data.getData());
          
          out.println(keyStuff);
          out.println(dataStuff);
          out.close();
          System.out.println("Results written to answer file.");

          // close the cursor, we are done
          cursor.close();
          
        } catch (IOException e) {
          System.err.println("PrintWriter error: " + e.toString());
          System.exit(1);
        }
      } else {
        // If we fail...
        System.out.println("Key DNE");
      }
    } catch (DatabaseException dbe) {
      System.err.println("DBE error: " + dbe.toString());
      System.exit(1);
    } catch (NullPointerException e) {
      System.err.println("Database Unpopulated!");
    }
  }

  public void findByDataB(String input) {
    System.out.println("Data");
  }

  public void findByRangeB(String lowInput, String upperInput) {
    DatabaseEntry lowKeyValue = new DatabaseEntry(lowInput.getBytes());
    lowKeyValue.setSize(lowInput.length());

    DatabaseEntry upperKeyValue = new DatabaseEntry(upperInput.getBytes());
    upperKeyValue.setSize(upperInput.length());

    DatabaseEntry key = new DatabaseEntry();
    DatabaseEntry data = new DatabaseEntry();

    try {
      // Create one cursor
      Cursor cursor = Globals.my_table.openCursor(null, null);

      // Start timer
      beforeTime = System.nanoTime();

      // make a while loop and execute the search
      // Start from first key larger and stop before first key smaller
      int count = 0; // use to tell if we got no results
      OperationStatus oprStatus = cursor.getFirst(key, data, LockMode.DEFAULT);
      while (oprStatus == OperationStatus.SUCCESS) {
        if ( key.compareTo(upperKeyValue) <= 0 && key.compareTo(lowKeyValue) ){
             count += 1;
          }
        
          oprStatus = cursor.getNext(key, data, LockMode.DEFAULT);
      }

      afterTime = System.nanoTime();
      queryTime = afterTime - beforeTime;
        queryTime *= 0.001;
        System.out.println("Query time: " + queryTime + " microseconds");
        System.out.println("Count: " + count);
      }

      try {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Globals.answers.getName(), true)));

        out.println("Range Search: " + queryTime);
        out.close();
        System.out.println("Results written.");
        
      } catch (IOException e) {
        System.err.println("PrintWriter error: " + e.toString());
        System.exit(1);
      }
      
      cursor.close();

    } catch (DatabaseException dbe) {
      System.err.println("DBE error: " + dbe.toString());
      System.exit(1);
    } catch (NullPointerException e) {
      System.err.println("Database Unpopulated!");
    }
  }
}
                       
