import java.util.*;
import com.sleepycat.db.*; //for Berkeley DB stuff
import java.io.*;

//this is acting as a global class!
//use like this: Login.VARIABLE,
// no need to call setters and getters as its a global variable c:


public class Globals{

  //the directory we are storing the data
  public static final String location = "/tmp/jlovas_db";
    
  //not really sure which one i need?
  public static File answers = new File("answers");
  
  //tracking the number entries - might not need this, will see                 
  public static int num = 0;

  //for creating the database - needs to be set with user's input
  //for what kind of database should be made (do that where login used to be ahh)
  public static DatabaseConfig dbConfig = new DatabaseConfig();
  public static Database my_table;
  public static final String db_filename = "db";

  // For creating/configuring secondary database, aka secondary index
  public static SecondaryConfig secDbConfig = new SecondaryConfig();
  public static SKC keyCreator = new SKC();
  public static SecondaryDatabase secDb;
  public static final String sdb_filename = "sdb";
}
