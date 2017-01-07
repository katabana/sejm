package sejm;

/**
 * Created by Kasia on 2017-01-07.
 */
public class ResultsPrinter {
    private String option;
    private String deputy;
    private float sum;
    private String[] results;
    private int result;

    public ResultsPrinter(String option, String deputy, float result){
        this.option = option;
        this.deputy = deputy;
        this.sum = result;
        this.printResults();
    }
    public ResultsPrinter(String option, float result) {
        this.option = option;
        this.sum = result;
        this.printResults();
    }

    public ResultsPrinter(String option, String[] result){
        this.results = result;
        this.printResults();
    }
    public ResultsPrinter(int termNo, String name){
        this.printResults(termNo, name);
    }

    private void printResults(int termNo, String name){
        System.out.println("There is no such deputy "+name+" in "+termNo+"th term.");
    }
    private void printResults(){
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
                System.out.println("Najwięcej podróży zagranicznych "+this.results[1]+" odbył "+this.results[0]);
                break;
        }
    }
}
