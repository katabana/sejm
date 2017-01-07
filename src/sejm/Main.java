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

public class Main {

    public static void main(String args[]){

        try {

            Deputies deputies = new Deputies(7);
            //deputies.getDeputyMostAbroadTrips(7);
            //deputies.getDeputyMostTimeAbroad(7);
            deputies.deputiesBeenInItaly(7);
            //deputies.getDeputyMostExpensiveTrip(7);

            //wydatki posla

            //int deputyID = deputies.getID("Małgorzata Wypych");
            //int deputyID = deputies.getID("Jan Dziedziczak");
            int deputyID = deputies.getID("Tadeusz Iwiński");
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
