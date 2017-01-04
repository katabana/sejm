package sejm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by Kasia on 2016-12-17.
 */
/** done :
 * officeSpendings
 * average sum of spendings
 * sum of deputy's spendings
 * deputy who had the most abroad trips
 * deputy who spent the most time abroad
 * more
 */
    //TODO: cleaning arguments: name - delete spaces
    //TODO: change expeditions -> trips
    //TODO: expeditions - how? hashmap like deputies? no idea
public class Main {

    public static void main(String args[]){

        try {
            ReadFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie.json");
            JSONObject obj = ReadFromURL.readJsonFromUrl("https://api-v3.mojepanstwo.pl/dane/poslowie.json");
            JSONArray root = (JSONArray) obj.get("Dataobject");

            Deputies deputies = new Deputies(8);
            //deputies.getDeputyMostAbroadExpeditions(7);
            //deputies.getDeputyMostTimeAbroad(7);
            //deputies.deputiesBeenInItaly(7);
            deputies.getDeputyMostExpensiveTrip(7);

            //wydatki posla

            //int deputyID = deputies.getID("Małgorzata Wypych");
            int deputyID = deputies.getID("Jan Dziedziczak");
            System.out.println(deputyID);
            /*
            if (deputyID >= 0) {
                System.out.println(deputyID);
                float spent = Deputy.getSpendings(deputyID, 7);
                System.out.println(spent);
            }
            else
                System.out.println("No such deputy.");
            */
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
