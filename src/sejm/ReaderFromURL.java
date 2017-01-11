package sejm;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
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

    public static JSONObject readExpensesFromUrl(int id) throws IOException, ParseException {
        return ReaderFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/"+id+".json?layers[]=wydatki");
    }

    public static JSONObject readTripsFromUrl(int id) throws IOException, ParseException {
        return ReaderFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/"+id+".json?layers[]=wyjazdy");
    }

    public static JSONObject readStartPage() throws IOException, ParseException {
        return ReaderFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie.json");
    }

    public static JSONArray makeLinksList(JSONObject obj) throws IOException, ParseException {
        JSONArray root = (JSONArray) obj.get("Dataobject");
        obj = (JSONObject) obj.get("Links");
        while(obj.get("next")!=null) {
            String url = obj.get("next").toString();
            obj = ReaderFromURL.readJsonFromUrl(url);
            root.addAll((JSONArray) obj.get("Dataobject"));
            obj = (JSONObject) obj.get("Links");
        }
        return root;
    }
}
