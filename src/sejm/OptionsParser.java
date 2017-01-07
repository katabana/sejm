package sejm;

import org.json.simple.parser.ParseException;

import javax.xml.transform.Result;
import java.io.IOException;
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

    public OptionsParser(String[] args) throws ParseException, IOException {
        this.termNo = Integer.parseInt(args[0]);
        this.dep = new Deputies(termNo);
        this.option = args[1];
        if(args.length > 2) {
            this.deputy = args[2];
            this.deputyID = dep.getID(this.deputy);
        }
    }

    public void parseOptions() throws ParseException, IOException{

        switch (this.option) {
            //sum of expenses of deputy
            case "a": {
                if(this.deputyID == -1){
                    new ResultsPrinter(this.termNo, this.deputy);
                    break;
                }
                float result = Deputy.getSpendings(this.deputyID, this.termNo);
                new ResultsPrinter("a", this.deputy, result);
                break;
            }
            //sum of expenses on 'drobne naprawy i remonty biura poselskiego' of deputy
            case "b": {
                if(this.deputyID == -1){
                    new ResultsPrinter(this.termNo, this.deputy);
                    break;
                }
                float result = Deputy.getOfficeSpendings(this.deputyID, this.termNo);
                new ResultsPrinter("b", this.deputy, result);
                break;
            }
            //average value of expenses of all deputies
            case "c": {
                float result = dep.avgSpendings(this.termNo);
                ResultsPrinter rp = new ResultsPrinter("c", result);
                break;
            }
            case "d": {
                String[] result = dep.getDeputyMostAbroadTrips(this.termNo);
                new ResultsPrinter("d", result);
                break;
            }



        }
    }
}
