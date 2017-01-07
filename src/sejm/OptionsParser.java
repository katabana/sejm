package sejm;

import java.util.HashMap;

/**
 * Created by Kasia on 2017-01-07.
 */

public class OptionsParser {
    private int termNo;
    private String option;
    private String deputy;
    private int deputyID;
    private Deputies dep;

    public OptionsParser(String[] args) {
        this.termNo = Integer.parseInt(args[0]);
        this.dep = new Deputies(termNo);
        this.option = args[1];
        if(args.length > 2) {
            this.deputy = args[2];
            this.deputyID = dep.getID(this.deputy);
        }
    }

    public void parseOptions(){

        switch (this.option) {
            //sum of expenses of deputy
            case "a": {
                float result = Deputy.getSpendings(this.deputyID, this.termNo);
                ResultsPrinter("a", this.deputy, result);
                break;
            }
            //sum of expenses on 'drobne naprawy i remonty biura poselskiego' of deputy
            case "b": {
                float result = Deputy.getOfficeSpendings(this.deputyID, this.termNo);
                ResultsPrinter("b", this.deputy, result);
                break;
            }
            //average value of expenses of all deputies
            case "c": {
                float result = Deputies.avgSpendings(this.termNo);
                ResultsPrinter("c", result);
                break;
            }
            case "d": {
                String[] result = dep.getDeputyMostAbroadTrips(this.termNo);
                ResultsPrinter("d", result);
                break;
            }



        }
    }
}
