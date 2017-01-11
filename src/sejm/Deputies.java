package sejm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Katabana on 2016-12-17.
 * //eventually have done handling different ways to get deputy - like two names + surname, just one name.
 */

public class Deputies {
    private HashMap<Integer, String> deputies;
    private HashMap<Integer, Float> spendings;
    private int termNo;

    public Deputies(int termNo) throws ParseException, IOException {
        this.deputies = new HashMap<>();
        this.spendings = new HashMap<>();
        this.termNo = termNo;
        this.makeDeputies();
    }

    public void printDeputies(){
        for(Map.Entry<Integer, String> entry : this.deputies.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private void makeDeputies() throws ParseException, IOException{

        JSONObject obj = ReaderFromURL.readStartPage();
        JSONArray root = ReaderFromURL.makeLinksList(obj);

        for (int i = 0; i < root.size(); i++) {
            JSONObject data = (JSONObject) root.get(i);
            int id = Integer.parseInt(data.get("id").toString());
            data = (JSONObject) data.get("data");
            //String name = data.get("poslowie.imie_pierwsze").toString() +" "+data.get("poslowie.nazwisko").toString();
            String name = data.get("ludzie.nazwa").toString();
            String term = data.get("poslowie.kadencja").toString();
            if(term.contains(new Integer(this.termNo).toString())) {
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


    public double avgSpendings() throws ParseException, IOException {
        double sum = 0;
        double avgTmp = 0;
        int records = 0;
        for(int id : this.deputies.keySet()){
            JSONObject obj = ReaderFromURL.readExpensesFromUrl(id);

            if(ArgsParser.rightTerm(obj, this.termNo)) {
                records++;
                obj = (JSONObject) obj.get("layers");
                obj = (JSONObject) obj.get("wydatki");
                JSONArray years = (JSONArray) obj.get("roczniki");
                for (int i = 0; i < years.size(); i++) {
                    JSONObject tmp = (JSONObject) years.get(i);
                    JSONArray spent = (JSONArray) tmp.get("pola");
                    for (int j = 0; j < spent.size(); j++) {
                        sum += Double.parseDouble(spent.get(j).toString());
                    }
                }
                avgTmp = (avgTmp * (records - 1) + sum) / records;
                sum = 0;
            }
        }

        double avg = sum / records;
        avg = (double) Math.round(avg * 100) / 100;      //rounding avg to two decimals
        avgTmp = (double) Math.round(avgTmp * 100) / 100;
        return avgTmp;
    }

    public String[] getDeputyMostAbroadTrips() throws ParseException, IOException {

        return Trips.findMostAbroadTrips(this.termNo, this.deputies);
    }

    public String[] getDeputyMostTimeAbroad() throws ParseException, IOException {

        return Trips.findDeputyMostTimeAbroad(this.termNo, this.deputies);
    }

    public String[] getDeputyMostExpensiveTrip() throws ParseException, IOException {

        return Trips.findDeputyMostExpensiveTrip(this.termNo, this.deputies);
    }

    public ArrayList<String> deputiesBeenInItaly() throws ParseException, IOException {
        ArrayList<String> deputiesList = new ArrayList<>();
        for(int deputyID : this.deputies.keySet()) {
            JSONObject obj = ReaderFromURL.readTripsFromUrl(deputyID);
            JSONObject data = (JSONObject) obj.get("data");

            if(ArgsParser.rightTerm(obj, this.termNo)) {
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
