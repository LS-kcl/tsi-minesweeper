package com.kclls.github;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoordTest {
    Coord coord;
    @BeforeAll
    public void setUp (){
      coord = new Coord(1, 2);
    }

    @Test
    public void canAccessCoordColDirectly(){
        int expected = 2;
        Assertions.assertEquals(expected, coord.col, "Must be able to access Coord col");
    }

    @Test
    public void canAccessCoordRowDirectly(){
        int expected = 1;
        Assertions.assertEquals(expected, coord.row, "Must be able to access Coord row");
    }

    @Test
    public void stringOutputsCorrectly() {
        String expected = "(1, 2)";
        Assertions.assertTrue(expected.equals(coord.getStrVersion()), "Coord is not returning the correct string");
    }

    /*
     * coord.row and coord.col are immutable, and throw compile errors
     * if we attempt to set a new value
     */
}
