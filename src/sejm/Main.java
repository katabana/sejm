package sejm;

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
// arguments: termNo, funkcja, [imie + nazwisko]
    //TODO: cleaning arguments: name - delete spaces

public class Main {

    public static void main(String args[]){
        System.out.println(args[2]);
        try {

            String m = ArgsParser.validArgs(args);
            System.out.println("���󶳿���");
            if(!m.isEmpty()) {
                System.out.println(m);
                return ;
            }
            OptionsParser op = new OptionsParser(args);
            op.parseOptions();

            //deputies.printDeputies();
            //deputies.getDeputyMostAbroadTrips(7);
            //deputies.getDeputyMostTimeAbroad(7);
            //deputies.deputiesBeenInItaly(7);
            //deputies.getDeputyMostExpensiveTrip(7);

            //wydatki posla

            //int deputyID = deputies.getID("Ma?gorzata Wypych");
            //int deputyID = deputies.getID("Jan Dziedziczak");
            //int deputyID = deputies.getID("Tadeusz Iwi?ski");
            //System.out.println(deputyID);
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
