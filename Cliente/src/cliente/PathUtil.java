package cliente;

import java.io.File;
import java.io.IOException;

public class PathUtil {

    public static String getImagePath(String fileName) {
        
        File file = new File("images/"+fileName);
        String path ="";

        try{
            path = file.getCanonicalPath();
            path="file:"+path;
        }catch(IOException ex){
          ex.printStackTrace();
        }

        return path;
    }
}