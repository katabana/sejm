package sejm;

import com.sun.org.apache.xpath.internal.Arg;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kasia on 08.01.17.
 */
class ArgsParserTest {

    @org.junit.jupiter.api.Test
    void parseArgs() {

        Assertions.assertArrayEquals(ArgsParser.parseArgs(new String[]{"8","a","JAn   DziEDziczak"}),new String[]{"8", "a","Jan Dziedziczak"});
        Assertions.assertArrayEquals(ArgsParser.parseArgs(new String[]{"8","b","Ma³goRZata    WypyCH"}), new String[]{"8","b","Ma³gorzata Wypych"});
        Assertions.assertTrue(ArgsParser.parseArgs(new String[]{"7","a","RomAN giERtycH"})[2].equals("Roman Giertych"));
        Assertions.assertArrayEquals(ArgsParser.parseArgs(new String[]{"6","a"}),new String[] {"6","a"});

        Assertions.assertFalse(ArgsParser.parseArgs(new String[]{"7","a","KaTarzyna Hall"})[2].equals("Katarzyna Wall"));
        Assertions.assertFalse(ArgsParser.parseArgs(new String[]{"7","a","KaTarzyna Hall"})[2].equals("Jan Dziedziczak"));

    }

    @org.junit.jupiter.api.Test
    void validArgs() {

        Assertions.assertEquals(ArgsParser.validArgs(new String[]{}),"Not enough arguments.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"a"}),"Not enough arguments.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"diasd"}),"Not enough arguments.");

        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"a","etwas"}),"a is a wrong term number. Choose 7 or 8.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"Kokanuga","etwas"}),"Kokanuga is a wrong term number. Choose 7 or 8.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"10","etwas"}),"10 is a wrong term number. Choose 7 or 8.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"-20","etwas"}),"-20 is a wrong term number. Choose 7 or 8.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"-20","etwas","kkk"}),"Too many [3] arguments.");

        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"8","10"}), "10 is not an available option.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"8","ab"}), "ab is not an available option.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"8","asjdaj"}), "asjdaj is not an available option.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"8","asjdaj","jjj"}), "Too many [3] arguments.");

        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"7","a"}), "You need one argument more. Probably deputy's name is lacking.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"7","b"}), "You need one argument more. Probably deputy's name is lacking.");

        Assertions.assertNotEquals(ArgsParser.validArgs(new String[]{"9","a"}), "You need one argument more. Probably deputy's name is lacking.");
        Assertions.assertNotEquals(ArgsParser.validArgs(new String[]{"8","i"}), "You need one argument more. Probably deputy's name is lacking.");
        Assertions.assertNotEquals(ArgsParser.validArgs(new String[]{"8","c"}), "You need one argument more. Probably deputy's name is lacking.");

        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"7","b","Wypych"}), ("\"Wypych\" is not a right deputy's name."));
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"7","b","WyPYch"}), ("\"Wypych\" is not a right deputy's name."));
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"7","b","Ma³gorzata Anna Monika Wypych"}), ("\"Ma³gorzata Anna Monika Wypych\" is not a right deputy's name."));

        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"7","a","Robert Maciaszek","Chrzanów"}),"Too many [4] arguments.");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"99","ii","Robert Maciaszek","Chrzanów","jjj"}),"Too many [5] arguments.");

        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"8","a","Ma³gorzata Wypych"}),"");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"7","b","Ma³gorzata Wypych"}),"");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"7","d"}),"");
        Assertions.assertEquals(ArgsParser.validArgs(new String[]{"8","e"}),"");

    }

    @org.junit.jupiter.api.Test
    void rightTerm() {

    }

}