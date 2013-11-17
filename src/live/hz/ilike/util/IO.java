package live.hz.ilike.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/15/13
 * Time: 9:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class IO {

    private static int count = 0;

    public static String inputStream2Str(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.toUpperCase().equals("EOF")
                        || line.equals("") || line == "") break;
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        count++;
        return sb.toString();
    }

}
