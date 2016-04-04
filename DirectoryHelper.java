import java.io.*;
import java.util.*;    
public class DirectoryHelper{
    public static void makeDirectory(String path){
        File file = new File(path);
        boolean isDirectoryCreated = file.mkdir();                

        if (isDirectoryCreated) {
            
        } else {
            deleteDir(file);  // Invoke recursive method
            file.mkdir();       
        }
    }

    public static void deleteDir(String path) {
        File file = new File(path);
        deleteDir(file);
    }
    
    public static void deleteDir(File dir) {
        File[] files = dir.listFiles();

        for (File myFile: files) {
            if (myFile.isDirectory()) {  
                deleteDir(myFile);
            } 
            myFile.delete();
        }

        dir.delete();
    }
}
