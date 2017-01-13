package sejm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by Katabana on 2016-12-17.
 */

public class Deputy {
    private int id;
    private int termNo;

    public Deputy(int id, int termNo){
        this.id = id;
        this.termNo = termNo;
    }

    public float getSpendings() throws ParseException, IOException {
        JSONObject obj = ReaderFromURL.readExpensesFromUrl(this.id);

        float sum = 0;
        if(validTerm(obj, this.termNo)){
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
        else sum = 0;
        sum = (float) Math.round(sum * 100) / 100;
        return sum;
    }

    //caluclates sum spent on "Drobne naprawy..."
    public float getOfficeSpendings() throws ParseException, IOException {
        JSONObject obj = ReaderFromURL.readExpensesFromUrl(this.id);
        float sum = 0;

        if(validTerm(obj, this.termNo)){
            obj = (JSONObject) obj.get("layers");
            obj = (JSONObject) obj.get("wydatki");
            JSONArray years = (JSONArray) obj.get("roczniki");
            for (int i = 0; i < years.size(); i++) {
                JSONObject tmp = (JSONObject) years.get(i);
                JSONArray spent = (JSONArray) tmp.get("pola");
                sum += Float.parseFloat(spent.get(13).toString());
            }
        }
        else sum = 0;
        sum = (float) Math.round(sum * 100) / 100;
        return sum;
    }

    private static boolean validTerm(JSONObject obj,int termNo){
        JSONObject data = (JSONObject) obj.get("data");
        String term = data.get("poslowie.kadencja").toString();
        return term.contains(Integer.toString(termNo));
    }

}
