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

        Assertions.assertArrayEquals(new ArgsParser(new String[]{"8","a","JAn   DziEDziczak"}).parseArgs(),new String[]{"8", "a","Jan Dziedziczak"});
        Assertions.assertArrayEquals(new ArgsParser(new String[]{"8","b","Ma³goRZata    WypyCH"}).parseArgs(), new String[]{"8","b","Ma³gorzata Wypych"});
        Assertions.assertTrue(new ArgsParser(new String[]{"7","a","RomAN giERtycH"}).parseArgs()[2].equals("Roman Giertych"));
        Assertions.assertArrayEquals(new ArgsParser(new String[]{"6","a"}).parseArgs(),new String[] {"6","a"});

        Assertions.assertFalse(new ArgsParser(new String[]{"7","a","KaTarzyna Hall"}).parseArgs()[2].equals("Katarzyna Wall"));
        Assertions.assertFalse(new ArgsParser(new String[]{"7","a","KaTarzyna Hall"}).parseArgs()[2].equals("Jan Dziedziczak"));

    }

    @org.junit.jupiter.api.Test
    void validArgs() {

        Assertions.assertEquals(new ArgsParser(new String[]{}).validArgs(),"Not enough arguments.");
        Assertions.assertEquals(new ArgsParser(new String[]{"a"}).validArgs(),"Not enough arguments.");
        Assertions.assertEquals(new ArgsParser(new String[]{"diasd"}).validArgs(),"Not enough arguments.");

        Assertions.assertEquals(new ArgsParser(new String[]{"a","etwas"}).validArgs(),"a is a wrong term number. Choose 7 or 8.");
        Assertions.assertEquals(new ArgsParser(new String[]{"Kokanuga","etwas"}).validArgs(),"Kokanuga is a wrong term number. Choose 7 or 8.");
        Assertions.assertEquals(new ArgsParser(new String[]{"10","etwas"}).validArgs(),"10 is a wrong term number. Choose 7 or 8.");
        Assertions.assertEquals(new ArgsParser(new String[]{"-20","etwas"}).validArgs(),"-20 is a wrong term number. Choose 7 or 8.");
        Assertions.assertEquals(new ArgsParser(new String[]{"-20","etwas","kkk"}).validArgs(),"Too many [3] arguments.");

        Assertions.assertEquals(new ArgsParser(new String[]{"8","10"}).validArgs(), "10 is not an available option.");
        Assertions.assertEquals(new ArgsParser(new String[]{"8","ab"}).validArgs(), "ab is not an available option.");
        Assertions.assertEquals(new ArgsParser(new String[]{"8","asjdaj"}).validArgs(), "asjdaj is not an available option.");
        Assertions.assertEquals(new ArgsParser(new String[]{"8","asjdaj","jjj"}).validArgs(), "Too many [3] arguments.");

        Assertions.assertEquals(new ArgsParser(new String[]{"7","a"}).validArgs(), "You need one argument more. Probably deputy's name is lacking.");
        Assertions.assertEquals(new ArgsParser(new String[]{"7","b"}).validArgs(), "You need one argument more. Probably deputy's name is lacking.");

        Assertions.assertNotEquals(new ArgsParser(new String[]{"9","a"}).validArgs(), "You need one argument more. Probably deputy's name is lacking.");
        Assertions.assertNotEquals(new ArgsParser(new String[]{"8","i"}).validArgs(), "You need one argument more. Probably deputy's name is lacking.");
        Assertions.assertNotEquals(new ArgsParser(new String[]{"8","c"}).validArgs(), "You need one argument more. Probably deputy's name is lacking.");

        Assertions.assertEquals(new ArgsParser(new String[]{"7","b","Wypych"}).validArgs(), ("\"Wypych\" is not a right deputy's name."));
        Assertions.assertEquals(new ArgsParser(new String[]{"7","b","WyPYch"}).validArgs(), ("\"Wypych\" is not a right deputy's name."));
        Assertions.assertEquals(new ArgsParser(new String[]{"7","b","Ma³gorzata Anna Monika Wypych"}).validArgs(), ("\"Ma³gorzata Anna Monika Wypych\" is not a right deputy's name."));

        Assertions.assertEquals(new ArgsParser(new String[]{"7","a","Robert Maciaszek","Chrzanów"}).validArgs(),"Too many [4] arguments.");
        Assertions.assertEquals(new ArgsParser(new String[]{"99","ii","Robert Maciaszek","Chrzanów","jjj"}).validArgs(),"Too many [5] arguments.");

        Assertions.assertEquals(new ArgsParser(new String[]{"8","a","Ma³gorzata Wypych"}).validArgs(),"");
        Assertions.assertEquals(new ArgsParser(new String[]{"7","b","Ma³gorzata Wypych"}).validArgs(),"");
        Assertions.assertEquals(new ArgsParser(new String[]{"7","d"}).validArgs(),"");
        Assertions.assertEquals(new ArgsParser(new String[]{"8","e"}).validArgs(),"");

    }

    @org.junit.jupiter.api.Test
    void rightTerm() {

    }

}