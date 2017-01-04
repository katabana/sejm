package sejm;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Created by Kasia on 2016-12-16.
 */

public class ReadFromURL {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static String cleanURL(String url) {
        String newurl = "";
        for (int i = 0; i < url.length(); i++) {
            if(url.charAt(i)!= '\\')
                newurl += url.charAt(i);
        }
        return newurl;
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, ParseException {
        url = cleanURL(url);
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
