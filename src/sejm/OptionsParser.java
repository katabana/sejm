package sejm;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;


/**
 * Created by Katabana on 2017-01-07.
 */

public class OptionsParser {
    private int argsCount;
    private int termNo;
    private String option;
    private String deputy;
    private int deputyID;
    private Deputies dep;

    public OptionsParser(String[] args) throws ParseException, IOException {
        this.termNo = Integer.parseInt(args[0]);
        this.dep = new Deputies(termNo);
        this.option = args[1];
        this.argsCount = args.length;
        if(this.argsCount == 3 && "ab".contains(this.option)) {
            this.deputy = args[2];
            this.deputyID = dep.getID(this.deputy);
        }

    }

    public void parseOptions() throws ParseException, IOException{

        switch (this.option) {
            //sum of expenses of deputy
            case "a": {
                if (this.deputyID == -1) {
                    new ResultsPrinter(this.termNo, this.deputy);
                    break;
                }
                float result = new Deputy(this.deputyID, this.termNo).getSpendings();
                new ResultsPrinter(this.termNo,"a", this.deputy, result);
                break;
            }

            //sum of expenses on 'drobne naprawy i remonty biura poselskiego' of deputy
            case "b": {
                if (this.deputyID == -1) {
                    new ResultsPrinter(this.termNo, this.deputy);
                    break;
                }
                float result = new Deputy(this.deputyID, this.termNo).getOfficeSpendings();
                new ResultsPrinter(this.termNo,"b", this.deputy, result);
                break;
            }

            //average value of expenses of all deputies
            case "c": {
                double result = dep.avgSpendings();
                new ResultsPrinter(this.termNo, "c", result);
                break;
            }

            //the biggest value of how many abroad trips had deputies
            case "d": {
                String[] results = dep.getDeputyMostAbroadTrips();
                new ResultsPrinter(this.termNo,"d", results);
                break;
            }

            //the biggest value of how long any deputy spend time abroad
            case "e": {
                String[] results = dep.getDeputyMostTimeAbroad();
                new ResultsPrinter(this.termNo,"e", results);
                break;
            }

            //the most expensive trip and deputy who had it
            case "f": {
                String[] results = dep.getDeputyMostExpensiveTrip();
                new ResultsPrinter(this.termNo,"f", results);
                break;
            }

            //list of deputies who has been to Italy
            case "g": {
                ArrayList<String> list = dep.deputiesBeenInItaly();
                new ResultsPrinter(this.termNo,"g", list);
                break;
            }

        }
    }
}
