package it.pa.unicam.stronatisamuele115894.model;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    Position p1;
    Position p2;


    private void createPositions(double x1, double y1, double x2, double y2) {
        p1 = new Position(x1,y1);
        p2 = new Position(x2,y2);
    }

    @AfterEach
    private void deallocate() {
        p1 = null;
        p2 = null;
    }

    @Test
    public void checkEquals() {
        createPositions(2,3,2,3);
        assertEquals(p1,p2);
        p2 = new Position(0,5);
        assertNotEquals(p1,p2);
        createPositions(2.768,3.324,2.76800,3.324000);
        assertEquals(p1,p2);
        p2 = new Position(3.5932,5.7883);
        assertNotEquals(p1,p2);
    }

    @Test
    public void checktoString() {
        createPositions(2,3,3.5932,5.7883);
        assertEquals("Position instance: x=2.0 y=3.0", p1.toString());
        assertEquals("Position instance: x=3.5932 y=5.7883", p2.toString());
    }

}
