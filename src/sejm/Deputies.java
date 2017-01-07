package sejm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Kasia on 2016-12-17.
 */
//list addAll;
public class Deputies {
    protected HashMap<Integer, String> deputies;
    protected HashMap<Integer, Float> spendings;

    public Deputies(int termNo) throws ParseException, IOException {
        this.deputies = new HashMap<Integer, String>();
        this.spendings = new HashMap<Integer, Float>();
        this.makeDeputies(termNo);
    }

    public void printDeputies(){
        for(Map.Entry<Integer, String> entry : this.deputies.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    //which term to calculate
    private void makeDeputies(int termNo) throws ParseException, IOException{

        JSONObject obj = (JSONObject) ReaderFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie.json");
        JSONArray root = (JSONArray) obj.get("Dataobject");
        obj = (JSONObject) obj.get("Links");
        while(obj.get("next")!=null) {
            String url = obj.get("next").toString();
            obj = (JSONObject) ReaderFromURL.readJsonFromUrl(url);
            root.addAll((JSONArray) obj.get("Dataobject"));
            obj = (JSONObject) obj.get("Links");
        }

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

        System.out.println("liczy wydatki");
        float sum = 0;
        int records = 0;
        for(int id : this.deputies.keySet()){
            JSONObject obj = (JSONObject) ReaderFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/"+id+".json?layers[]=wydatki");
            JSONObject data = (JSONObject) obj.get("data");
            String term = data.get("poslowie.kadencja").toString();
            //7 as only records from 7th term are on
            //in future I would check years of term
            if(term.contains(Integer.toString(7))) {
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

    //TODO: in general are there trips from 8th term?
    public int getDeputyMostAbroadTrips(int termNo) throws ParseException, IOException {

        int expeditions = 0;
        int id = 0;
        int amount = 0;
        for(int deputyID : this.deputies.keySet()){
            JSONObject obj = (JSONObject) ReaderFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/"+deputyID+".json?layers[]=wyjazdy");
            JSONObject data = (JSONObject) obj.get("data");
            String term = data.get("poslowie.kadencja").toString();
            //7 as only records from 7th term are on
            //in the future I would check years of term
            if(term.contains(Integer.toString(7))) {
                amount = Integer.parseInt(data.get("poslowie.liczba_wyjazdow").toString());
                if (amount > expeditions) {
                    expeditions = amount;
                    id = deputyID;
                }
            }
        }
        System.out.println("Najwięcej podróży zagranicznych "+expeditions+" odbył "+deputies.get(id));
        return id;
    }

    //TODO: change so it checks years
    public int getDeputyMostTimeAbroad(int termNo) throws ParseException, IOException {
        int timeMax = 0;
        int id = 0;
        for(int deputyID : this.deputies.keySet()){
            int time = 0;
            JSONObject obj = (JSONObject) ReaderFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/"+deputyID+".json?layers[]=wyjazdy");
            JSONObject data = (JSONObject) obj.get("data");

            String term = data.get("poslowie.kadencja").toString();
            int tripsNumber = Integer.parseInt(data.get("poslowie.liczba_wyjazdow").toString());

            obj = (JSONObject) obj.get("layers");

            if(tripsNumber > 1) {           //as "wyjazdy" is stored as an array if it is not empty
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

            //7 as only records from 7th term are on
            //in the future I would check years of term
            /*
            if(term.contains(Integer.toString(7))) {
                amount = Integer.parseInt(data.get("poslowie.liczba_wyjazdow").toString());
                if (amount > trips) {
                    trips = amount;
                    id = deputyID;
                }
            } */
        }
        System.out.println("Poseł "+deputies.get(id)+" spędził najwięcej czasu "+timeMax+" dni za granicą.");

        return id;
    }

    public int getDeputyMostExpensiveTrip(int termNo) throws ParseException, IOException {
        float expendituresMax = 0;
        int id = 0;
        for(int deputyID : this.deputies.keySet()){
            float expenditures = 0;
            JSONObject obj = (JSONObject) ReaderFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/"+deputyID+".json?layers[]=wyjazdy");
            JSONObject data = (JSONObject) obj.get("data");

            String term = data.get("poslowie.kadencja").toString();
            int tripsNumber = Integer.parseInt(data.get("poslowie.liczba_wyjazdow").toString());

            obj = (JSONObject) obj.get("layers");

            if(tripsNumber > 1) {           //as "wyjazdy" is stored as an array if it is not empty
                JSONArray wyjazdy = (JSONArray) obj.get("wyjazdy");
                for (int i = 0; i < wyjazdy.size(); i++) {
                    JSONObject singleTrip = (JSONObject) wyjazdy.get(i);
                    if (singleTrip != null)
                        expenditures += Float.parseFloat(singleTrip.get("koszt_suma").toString());
                }
            }
            if (expenditures > expendituresMax) {
                id = deputyID;
                expendituresMax = expenditures;
            }

        }
        System.out.println("Poseł "+deputies.get(id)+" odbył najdroższą podróż o wartości "+expendituresMax+" zł.");
        return id;
    }

    public void deputiesBeenInItaly(int termNo) throws ParseException, IOException {
        String deputiesList = "";
        for(int deputyID : this.deputies.keySet()) {
            JSONObject obj = (JSONObject) ReaderFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie/"+deputyID+".json?layers[]=wyjazdy");
            JSONObject data = (JSONObject) obj.get("data");

            String term = data.get("poslowie.kadencja").toString();
            int tripsNumber = Integer.parseInt(data.get("poslowie.liczba_wyjazdow").toString());

            obj = (JSONObject) obj.get("layers");
            Boolean beenInItaly = false;
            if(tripsNumber > 1) {           //as "wyjazdy" is stored as an array if it is not empty
                JSONArray wyjazdy = (JSONArray) obj.get("wyjazdy");
                for (int i = 0; i < wyjazdy.size(); i++) {
                    JSONObject singleTrip = (JSONObject) wyjazdy.get(i);
                    if (singleTrip != null)
                        if (singleTrip.get("kraj").toString().equals("Włochy"))
                            beenInItaly = true;
                }
            }
            if(beenInItaly == true)
                deputiesList += this.deputies.get(deputyID)+"\n";
        }
        System.out.println(deputiesList);
    }

}
