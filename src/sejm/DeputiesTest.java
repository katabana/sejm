package sejm;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kasia on 08.01.17.
 */
class DeputiesTest {
    @Test
    void getID() throws ParseException,IOException {
        Deputies d8 = new Deputies(8);
        Deputies d7 = new Deputies(7);

        Assertions.assertEquals(d8.getID("Jan Dziedziczak"),81);
        Assertions.assertEquals(d8.getID("Ma³gorzata Wypych"),1392);
        Assertions.assertEquals(d7.getID("Robert Biedroñ"),26);
        Assertions.assertEquals(d7.getID("Robert Maciaszek"),1154);


    }

    @Test
    void avgSpendings() throws ParseException, IOException {

        Deputies d8 = new Deputies(8);
        Deputies d7 = new Deputies(7);

        Assertions.assertEquals(272247.61, d7.avgSpendings());
        Assertions.assertEquals(144882.97, d8.avgSpendings());

    }

    @Test
    void getDeputyMostAbroadTrips() throws ParseException, IOException {

        Deputies d8 = new Deputies(8);
        Deputies d7 = new Deputies(7);

        Assertions.assertEquals(Integer.toString(72), d7.getDeputyMostAbroadTrips()[1]);
        Assertions.assertEquals("Tadeusz Iwiñski", d7.getDeputyMostAbroadTrips()[0]);
        Assertions.assertEquals(Integer.toString(47), d8.getDeputyMostAbroadTrips()[1]);
        Assertions.assertEquals("Jan Dziedziczak", d8.getDeputyMostAbroadTrips()[0]);
    }

    @Test
    void getDeputyMostTimeAbroad() {

    }

    @Test
    void getDeputyMostExpensiveTrip() {

    }

    @Test
    void deputiesBeenInItaly() {

    }

}