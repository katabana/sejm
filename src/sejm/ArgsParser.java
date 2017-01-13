package sejm;

import org.apache.commons.lang3.text.WordUtils;
import org.json.simple.JSONObject;

/**
 * Created by Katabana on 2017-01-07.
 */

public class ArgsParser {
    private String[] args;

    public ArgsParser(String[] args){
        this.args = args;
    }

    public String[] parseArgs (){
        if(this.args.length == 3) {
            this.args[2] = makeName(this.args[2]);
            return this.args;
        }
        return this.args;
    }

    public String validArgs () {
        if(this.args.length < 2) {
            return "Not enough arguments";
        }
        String termText = this.args[0];
        boolean notNumber = false;
        for (int i = 0; i < this.args[0].length(); i++){
            if(!Character.isDigit(termText.charAt(i)))
                notNumber = true;
        }
        if(notNumber)
            return (termText+" is a wrong term number. Choose 7 or 8.");

        int term = Integer.parseInt(this.args[0]);
        if(!validTerm(term))
            return (term+" is a wrong term number. Choose 7 or 8.");

        String option = this.args[1];
        if(!rightOptionFormat(option))
            return (option+" is not an available option.");

        if(this.args.length == 2 && "ab".contains(this.args[1]))
            return "You need one argument more. Probably deputy's name is lacking.";

        if(this.args.length == 3 && "ab".contains(this.args[1])) {
            String name = this.args[2];
            name = makeName(name);
            if (name.split(" ").length < 2) {
                return ("\""+name +"\" is not a right deputy's name.");
            }
        }
        else if(this.args.length >= 3)
            return ("Too many ["+this.args.length+"] arguments.");

        return "";
    }

    //Because there is only 7th and 8th term yet
    private static boolean validTerm(int n){
        return (n == 7 || n == 8);
    }

    public static boolean rightTerm(JSONObject obj, int termNo){
        JSONObject data = (JSONObject) obj.get("data");
        String term = data.get("poslowie.kadencja").toString();
        return term.contains(Integer.toString(termNo));
    }

    //List of options in string
    private static boolean rightOptionFormat(String option){
        if(option.length() > 1)
            return false;
        return "abcdefg".contains(option);
    }

    private static String makeName(String name){
        //removes unnecessary spaces, makes sure capitalization of letters is right
        name = removeSpaces(name);
        name = WordUtils.capitalizeFully(name);
        if (name.contains("-"))
            name = WordUtils.capitalize(name, '-');
        return name;
    }

    private static String removeSpaces(String name){
        return name.replaceAll("\\s+"," ");
    }

}
