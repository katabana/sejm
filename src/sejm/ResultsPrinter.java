package sejm;

import java.util.ArrayList;

/**
 * Created by Katabana on 2017-01-07.
 */
public class ResultsPrinter {
    private String option;
    private String deputy;
    private float sum;
    private double result;
    private String[] results;
    private ArrayList<String> list;

    public ResultsPrinter(int termNo, String option, String deputy, float result){
        this.option = option;
        this.deputy = deputy;
        this.sum = result;
        this.printResults(termNo);
    }
    public ResultsPrinter(int termNo, String option, double result) {
        this.option = option;
        this.result = result;
        this.printResults(termNo);
    }

    public ResultsPrinter(int termNo, String option, String[] result){
        this.option = option;
        this.results = result;
        this.printResults(termNo);
    }
    public ResultsPrinter(int termNo, String option, ArrayList<String> list) {
        this.option = option;
        this.list = list;
        this.printResults(termNo);
    }
    public ResultsPrinter(int termNo, String name){
        this.printResults(termNo, name);
    }

    private void printResults(int termNo, String name){
        System.out.println("There is no such deputy "+name+" in "+termNo+"th term.");
    }

    private void printResults(int termNo){
        switch (this.option){
            case "a":
                System.out.println("Sum of expenses of "+this.deputy+" is "+this.sum+".");
                break;
            case "b":
                System.out.println("Sum of expenses on 'drobne naprawy i remonty biura poselskiego' of "+this.deputy+" is "+this.sum+".");
                break;
            case "c":
                System.out.println("Average value of sum of expenses of all deputies is "+this.sum+".");
                break;
            case "d":
                System.out.println("Most foreign trips "+this.results[1]+" had deputy "+this.results[0]);
                break;
            case "e":
                System.out.println("Deputy "+this.results[0]+" from "+termNo+"th term spend most time "+this.results[1]+" days abroad.");
                break;
            case "f":
                System.out.println("Deputy "+this.results[0]+" from "+termNo+"th term had the most expensive abroad trip which cost was "+this.results[1]+" z³.");
                break;
            case "g":
                System.out.println("Deputies who has been to Italy: ");
                for(int i = 0; i < this.list.size(); i++) {
                    System.out.println(this.list.get(i));
                }
                break;

        }
    }
}
