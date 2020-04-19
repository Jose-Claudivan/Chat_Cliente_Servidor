package cliente;

import java.io.File;
import java.io.IOException;

public class PathUtil {

    public static String getImagePath(String fileName) {
        String base = System.getProperty("user.dir");
        File file = new File(base+"/images/"+fileName);
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