package servidor;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {


    public static String getTime(){
        
        Format f = new SimpleDateFormat("HH:mm");
        String strResult = f.format(new Date());
        
        return strResult;
    }
}