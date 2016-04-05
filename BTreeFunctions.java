import java.util.*;
import com.sleepycat.db.*;
import java.io.*;
import java.io.File.*;

// This class will handle the cases of using btree
public class BTreeFunctions {

  long beforeTime, afterTime, queryTime;

  public void findByKeyB(String input) {
    System.out.println("Key");
  }

  public void findByDataB(String input) {
    System.out.println("Data");
  }

  public void findByRangeB(String lowInput, String upperInput) {
    System.out.println("Range");
  }
}
                       
