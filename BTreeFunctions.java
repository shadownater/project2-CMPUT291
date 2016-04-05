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
    System.out.println("Range");
  }
}
                       
