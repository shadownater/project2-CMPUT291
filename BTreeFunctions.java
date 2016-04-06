import java.util.*;
import com.sleepycat.db.*;
import java.io.*;
import java.io.File.*;
import java.nio.charset.*;

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
      int count = 0;

      // Start time
      beforeTime = System.nanoTime();

      // execute search
      OperationStatus oprStatus = cursor.getSearchKey(keyValue, data, null);

      // Handle Results:
      if ( oprStatus == OperationStatus.SUCCESS ) {
        // If we succeed...
        count += 1;

        // Calculate query time
        afterTime = System.nanoTime();

        queryTime = afterTime - beforeTime;
        queryTime *= 0.001;
        System.out.println("Records retrieved: " + count + ", Query time: " + queryTime + " microseconds");

        // Save the query time to a file
        try {
          PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Globals.answers.getName(), true)));
          
          String keyStuff = new String(keyValue.getData());
          String dataStuff = new String(data.getData());

          // One line for the key string:
          out.println(keyStuff);

          // One line for the data string:
          out.println(dataStuff);

          // And one empty line:
          out.println();
          
          out.close();
          System.out.println("Results written to answer file.");

          // close the cursor, we are done
          cursor.close();
          
        } catch (IOException e) {
          System.err.println("PrintWriter error: " + e);
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
    DatabaseEntry keyValue = new DatabaseEntry();
    DatabaseEntry data = new DatabaseEntry();
    
    try {
      // Create a curson
      Cursor cursor = Globals.my_table.openCursor(null, null);
      int count = 0;
      
      // Start time
      beforeTime = System.nanoTime();

      OperationStatus oprStatus = cursor.getFirst(keyValue, data, null);
      
      while( oprStatus == OperationStatus.SUCCESS ) {
        String s1 = new String( data.getData() , StandardCharsets.UTF_8 );
        if (s1.equals(input)) {
          count += 1;

          try {
            PrintWriter out = new PrintWriter(
              new BufferedWriter(new FileWriter(Globals.answers.getName(), true)));

            String keyStuff = new String(keyValue.getData());
            String dataStuff = new String(data.getData());

            // one line for the key string:
            out.println(keyStuff);

            // one line for the data string:
            out.println(dataStuff);

            // and one line for an empty String:
            out.println();

            out.close();
            
          } catch (IOException e) {
            System.err.println("PrintWriter error: " + e);
            System.exit(1);
          }

          cursor.close();

          System.out.println("Records retrieved: " + count + ", Execution time: " + queryTime + " microseconds");
          
          return;
        }
        
        data = new DatabaseEntry();
        oprStatus = cursor.getNext(keyValue, data, null);
      }

      System.out.println("Data DNE");

    } catch (DatabaseException dbe) {
      System.err.println("DBE error: " + dbe.toString());
      System.exit(1);
    } catch (NullPointerException e) {
      System.err.println("Database Unpopulated!");
    }
  }

  public void findByRangeB(String lowInput, String upperInput) {
    /*DatabaseEntry lowKeyValue = new DatabaseEntry(lowInput.getBytes());
    lowKeyValue.setSize(lowInput.length());

    DatabaseEntry upperKeyValue = new DatabaseEntry(upperInput.getBytes());
    upperKeyValue.setSize(upperInput.length());
    
    DatabaseEntry data= new DatabaseEntry();
    DatabaseEntry key = new DatabaseEntry();

    String d;
    
    try {
      // Create cursors and oprStatus
      Cursor cursorLow = Globals.my_table.openCursor(null, null);
      Cursor cursorUpper = Globals.my_table.openCursor(null, null);

      OperationStatus oprStatus;
      
      // Start timer
      int count = 0;
      beforeTime = System.nanoTime();

      // make a while loop and execute the search
      // Start counting from first key larger and stop before first key smaller
      oprStatus = cursorLow.getFirst(lowKeyValue, data, null);
      while (oprStatus == OperationStatus.SUCCESS) {

        // If key >= lower and key <= upper:
        String d = String(data.getData());
        
        System.out.println("\nData: " + stuff);

        oprStatus = cursorLow.getNext(lowKeyValue, data, null);
      }

      afterTime = System.nanoTime();
      queryTime = afterTime - beforeTime;
      queryTime *= 0.001;
      System.out.println("Query time: " + queryTime + " microseconds");
      System.out.println("Count: " + count);

      try {
        PrintWriter out = new PrintWriter(
          new BufferedWriter(new FileWriter(Globals.answers.getName(), true)));

        out.println("Range Search: " + queryTime);
        out.close();
        System.out.println("Results written.");
        
      } catch (IOException e) {
        System.err.println("PrintWriter error: " + e.toString());
        System.exit(1);
      }

      cursorLow.close();
      cursorUpper.close();

    } catch (DatabaseException dbe) {
      System.err.println("DBE error: " + dbe.toString());
      System.exit(1);
    } catch (NullPointerException e) {
      System.err.println("Database Unpopulated!");
    }
    }*/
  }
}
                       
