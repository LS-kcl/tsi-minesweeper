package com.kclls.github;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class CoordTest {

    @Test
    public void canAccessCoordColDirectly(){
        Coord c = new Coord(1,1);
        Assertions.assertEquals(1, c.col);
    }
}
