package sejm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Katabana on 2016-12-17.
 * //eventually have done handling different ways to get deputy - like two names + surname, just one name.
 */

public class Deputies {
    protected HashMap<Integer, String> deputies;
    protected HashMap<Integer, Float> spendings;

    public Deputies(int termNo) throws ParseException, IOException {
        this.deputies = new HashMap<>();
        this.spendings = new HashMap<>();
        this.makeDeputies(termNo);
    }

    public void printDeputies(){
        for(Map.Entry<Integer, String> entry : this.deputies.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private void makeDeputies(int termNo) throws ParseException, IOException{

        JSONObject obj = ReaderFromURL.readStartPage();
        JSONArray root = ReaderFromURL.makeLinksList(obj);

        for (int i = 0; i < root.size(); i++) {
            JSONObject data = (JSONObject) root.get(i);
            int id = Integer.parseInt(data.get("id").toString());
            data = (JSONObject) data.get("data");
            //String name = data.get("poslowie.imie_pierwsze").toString() +" "+data.get("poslowie.nazwisko").toString();
            String name = data.get("ludzie.nazwa").toString();
            String term = data.get("poslowie.kadencja").toString();
            if(term.contains(new Integer(termNo).toString())) {
                this.deputies.put(id, name);
            }
        }
    }

    public int getID(String name) throws ParseException, IOException {
        if(name != null && this.deputies.containsValue(name)) {
            for (Map.Entry<Integer, String> entry : this.deputies.entrySet()) {
                if (Objects.equals(name, entry.getValue()))
                    return entry.getKey();
            }
        }
        return -1;
    }


    public float avgSpendings(int termNo) throws ParseException, IOException {
        float sum = 0;
        int records = 0;
        for(int id : this.deputies.keySet()){
            JSONObject obj = ReaderFromURL.readExpensesFromUrl(id);

            if(ArgsParser.rightTerm(obj, termNo)) {
                records++;
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
        }
        float avg = sum / records;
        avg = (float) Math.round(avg * 100) / 100;      //rounding avg to two decimals
        return avg;
    }

    public String[] getDeputyMostAbroadTrips(int termNo) throws ParseException, IOException {

        return Trips.findMostAbroadTrips(termNo, this.deputies);
    }

    public String[] getDeputyMostTimeAbroad(int termNo) throws ParseException, IOException {

        return Trips.findDeputyMostTimeAbroad(termNo, this.deputies);
    }

    public String[] getDeputyMostExpensiveTrip(int termNo) throws ParseException, IOException {

        return Trips.findDeputyMostExpensiveTrip(termNo, this.deputies);
    }

    public ArrayList<String> deputiesBeenInItaly(int termNo) throws ParseException, IOException {
        ArrayList<String> deputiesList = new ArrayList<>();
        for(int deputyID : this.deputies.keySet()) {
            JSONObject obj = ReaderFromURL.readTripsFromUrl(deputyID);
            JSONObject data = (JSONObject) obj.get("data");

            if(ArgsParser.rightTerm(obj, termNo)) {
                int tripsNumber = Integer.parseInt(data.get("poslowie.liczba_wyjazdow").toString());

                obj = (JSONObject) obj.get("layers");
                Boolean beenInItaly = false;
                if (tripsNumber > 1) {           //as "wyjazdy" is stored as an array if it is not empty
                    JSONArray wyjazdy = (JSONArray) obj.get("wyjazdy");
                    for (int i = 0; i < wyjazdy.size(); i++) {
                        JSONObject singleTrip = (JSONObject) wyjazdy.get(i);
                        if (singleTrip != null) {
                            String country = singleTrip.get("kraj").toString();
                            if (country.equals("W³ochy"))
                                beenInItaly = true;
                        }
                    }
                }
                if (beenInItaly == true) {
                    deputiesList.add(this.deputies.get(deputyID));
                }
            }
        }
        return deputiesList;
    }


}
