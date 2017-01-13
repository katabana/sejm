package sejm;

import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by Katabana on 2016-12-17.
 */
/**         Args: termNo option [name + surname]
 *          deputy's name has to be in "" quotes.
 *
 * options:
 * a    -   sum of expenses of deputy, deputy's name obligatory
 * b    -   sum of expenses on 'drobne naprawy i remonty biura poselskiego' of deputy, deputy's name obligatory
 * c    -   average value of expenses of all deputies
 * d    -   the biggest value of how many abroad trips had deputies and who had it
 * e    -   the biggest value of how long deputies spent time abroad and who did it
 * f    -   the most expensive trip and deputy who had it
 * g    -   list of deputies who has been to Italy
 *
 *  e. g. 8 a "Ma³gorzata Wypych",
 *        7 g,
 *        8 c
 */


public class Application {

    public static void main(String args[]){

        try {

            String m = new ArgsParser(args).validArgs();
            if(!m.isEmpty()) {
                System.out.println(m);
                return ;
            }
            OptionsParser op = new OptionsParser(new ArgsParser(args).parseArgs());
            op.parseOptions();


        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
