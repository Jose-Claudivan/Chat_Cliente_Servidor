package cliente;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PathUtil {

    public static String getImagePath(String fileName) {
        
        String path ="";

        path = Paths.get("images/"+fileName).toAbsolutePath().toString();
        path="file:"+path;
        System.out.print(path);
       
        return path;
    }
}