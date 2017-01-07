package sejm;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by Kasia on 2017-01-07.
 */
//TODO: integrity conditions -? if option a then it has to have 3 args
public class ArgsParser {

    public static String[] parseArgs (String[] args){
        if(args.length == 3) {
            args[2] = makeName(args[2]);
            return args;
        }
        return args;
    }

    public static String validArgs (String[] args) {
        if(args.length < 2) {
            return "Not enough arguments";
        }
        int term = Integer.parseInt(args[0]);
        if(!rightTerm(term))
            return (term+" is a wrong term number. Choose 7 or 8.");

        String option = args[1];
        if(!rightOptionFormat(option))
            return (option+" is not an available option.");

        if(args.length >= 2) {
            String name = args[2];
            name = makeName(name);
            if (name.split(" ").length < 2) {
                return ("\""+name +"\" is not a right deputy's name.");
            }
        }
        return "";
    }

    //Because there is only 7th and 8th term yet
    private static boolean rightTerm(int n){
        return (n == 7 || n == 8);
    }

    //List of options in string
    private static boolean rightOptionFormat(String option){
        if(option.length() > 1)
            return false;
        return "abcdef".contains(option);
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
