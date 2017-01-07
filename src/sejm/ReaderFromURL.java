package sejm;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Created by Katabana on 2016-12-16.
 */

public class ReaderFromURL {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    public static JSONObject readJsonFromUrl(String url) throws IOException, ParseException {
        //repairs url as it contains '\' before '/'
        url.replace("\\", "");
        InputStream is = new URL(url).openStream();
        try {
            JSONParser parser = new JSONParser();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            Object obj = parser.parse(jsonText);
            JSONObject jsonObject = (JSONObject) obj;

            return jsonObject;
        }
        finally {
            is.close();
        }
    }
}
