package sejm;

import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by Katabana on 2016-12-17.
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

public class Main {

    public static void main(String args[]){

        try {

            String m = ArgsParser.validArgs(args);
            if(!m.isEmpty()) {
                System.out.println(m);
                return ;
            }
            OptionsParser op = new OptionsParser(ArgsParser.parseArgs(args));
            op.parseOptions();

            //deputies.printDeputies();
            //deputies.getDeputyMostAbroadTrips(7);
            //deputies.getDeputyMostTimeAbroad(7);
            //deputies.deputiesBeenInItaly(7);
            //deputies.getDeputyMostExpensiveTrip(7);

            //wydatki posla
            //int deputyID = deputies.getID("Ma?gorzata Wypych");
            //int deputyID = deputies.getID("Jan Dziedziczak");
            //System.out.println(deputyID);

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
