import java.util.*;
import com.sleepycat.db.*; //for Berkeley DB stuff
import java.io.*;

//this is acting as a global class!
//use like this: Login.VARIABLE, no need to call setters and getters as its a global variable c:


public class Globals{

  //the place we are storing the data
  //**NEED to code a way to create this and remove it after testing!!!!!
  public static final String location = "/tmp/jlovas_db"; 

  //tracking the number entries - might not need this, will see                 
  public static int num = 0;

  //for creating the database - needs to be set with user's input
  //for what kind of database should be made (do that where login used to be ahh)
  public static DatabaseConfig dbConfig = new DatabaseConfig();

  public static Database my_table;
}
