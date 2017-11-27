package highwho.com.formatdemo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by yinpengcheng on 2017/11/24.
 */

public class format {

    public static void main(String [] args){
        String str = "2017-11-24T10:01:41Z";
        String string = str.replace("T"," ").replace("Z","");
        System.out.println(string);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println(string2Date(string, simpleDateFormat));
    }
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
