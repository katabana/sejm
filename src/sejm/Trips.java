package sejm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by kasia on 08.01.17.
 */
public class Trips {

    public static String[] findMostAbroadTrips(int termNo, HashMap<Integer, String> deputies) throws ParseException, IOException {
        int expeditions = 0;
        int id = 0;
        int amount = 0;
        String[] result = new String[2];
        for(int deputyID : deputies.keySet()){
            JSONObject obj = ReaderFromURL.readTripsFromUrl(deputyID);
            JSONObject data = (JSONObject) obj.get("data");

            if(ArgsParser.rightTerm(obj, termNo)) {
                amount = Integer.parseInt(data.get("poslowie.liczba_wyjazdow").toString());
                if (amount > expeditions) {
                    expeditions = amount;
                    id = deputyID;
                }
            }
        }
        result[0] = deputies.get(id);
        result[1] = Integer.toString(expeditions);
        return result;

    }

    public static String[] findDeputyMostTimeAbroad(int termNo, HashMap<Integer, String> deputies) throws ParseException, IOException {
        int timeMax = 0;
        int id = 0;
        String[] result = new String[2];
        for(int deputyID : deputies.keySet()){
            int time = 0;
            JSONObject obj = ReaderFromURL.readTripsFromUrl(deputyID);
            JSONObject data = (JSONObject) obj.get("data");

            if(ArgsParser.rightTerm(obj, termNo)) {
                int tripsNumber = Integer.parseInt(data.get("poslowie.liczba_wyjazdow").toString());

                obj = (JSONObject) obj.get("layers");

                if (tripsNumber > 1) {           //as "wyjazdy" is stored as an array if it is not empty
                    JSONArray wyjazdy = (JSONArray) obj.get("wyjazdy");
                    for (int i = 0; i < wyjazdy.size(); i++) {
                        JSONObject singleTrip = (JSONObject) wyjazdy.get(i);
                        if (singleTrip != null)
                            time += Integer.parseInt(singleTrip.get("liczba_dni").toString());
                    }
                }
                if (time > timeMax) {
                    id = deputyID;
                    timeMax = time;
                }
            }

        }
        result [0] = deputies.get(id);
        result[1] = Integer.toString(timeMax);

        return result;
    }

    public static String[] findDeputyMostExpensiveTrip(int termNo, HashMap<Integer,String> deputies) throws ParseException, IOException {
        float expendituresMax = 0;
        int id = 0;
        String[] result = new String[2];

        for(int deputyID : deputies.keySet()){
            float expenditures = 0;
            JSONObject obj = ReaderFromURL.readTripsFromUrl(deputyID);
            JSONObject data = (JSONObject) obj.get("data");

            if(ArgsParser.rightTerm(obj, termNo)) {
                int tripsNumber = Integer.parseInt(data.get("poslowie.liczba_wyjazdow").toString());

                obj = (JSONObject) obj.get("layers");

                if (tripsNumber > 1) {           //as "wyjazdy" is stored as an array if it is not empty
                    JSONArray wyjazdy = (JSONArray) obj.get("wyjazdy");
                    for (int i = 0; i < wyjazdy.size(); i++) {
                        JSONObject singleTrip = (JSONObject) wyjazdy.get(i);
                        if (singleTrip != null) {
                            float exp = Float.parseFloat(singleTrip.get("koszt_suma").toString());
                            if(exp > expenditures)
                                expenditures = exp;
                        }
                    }
                }
                if (expenditures > expendituresMax) {
                    id = deputyID;
                    expendituresMax = expenditures;
                }
            }

        }
        result[0] = deputies.get(id);
        result[1] = Float.toString(expendituresMax);

        return result;
    }
}
