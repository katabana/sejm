package sejm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kasia on 2016-12-17.
 */

public class Deputy {
    private String name;

    public Deputy(JSONObject d){

    }

    public static float getSpendings(int id, int termNo) throws ParseException, IOException {
        JSONObject obj = (JSONObject) ReadFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/"+id+".json?layers[]=wydatki");
        JSONObject data = (JSONObject) obj.get("data");
        String term = data.get("poslowie.kadencja").toString();
        float sum = 0;
        if(term.contains(Integer.toString(7))){
            obj = (JSONObject) obj.get("layers");
            obj = (JSONObject) obj.get("wydatki");
            JSONArray years = (JSONArray) obj.get("roczniki");
            for (int i = 0; i < years.size(); i++) {
                JSONObject tmp = (JSONObject) years.get(i);
                JSONArray spent = (JSONArray) tmp.get("pola");
                for (int j = 0; j < spent.size(); j++) {
                    sum += Float.parseFloat(spent.get(j).toString());
                }
            }
        }
        sum = (float) Math.round(sum * 100) / 100;
        return sum;
    }

    //caluclates sum spent on "Drobne naprawy..."
    public static float getOfficeSpendings(int id, int termNo) throws ParseException, IOException {
        JSONObject obj = (JSONObject) ReadFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/"+id+".json?layers[]=wydatki");
        JSONObject data = (JSONObject) obj.get("data");
        String term = data.get("poslowie.kadencja").toString();
        float sum = 0;
        if(term.contains(Integer.toString(7))){
            obj = (JSONObject) obj.get("layers");
            obj = (JSONObject) obj.get("wydatki");
            JSONArray years = (JSONArray) obj.get("roczniki");
            for (int i = 0; i < years.size(); i++) {
                JSONObject tmp = (JSONObject) years.get(i);
                JSONArray spent = (JSONArray) tmp.get("pola");
                sum += Float.parseFloat(spent.get(13).toString());
            }
        }
        sum = (float) Math.round(sum * 100) / 100;
        return sum;
    }

}
