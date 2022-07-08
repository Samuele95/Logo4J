package it.pa.unicam.stronatisamuele115894.io.modelWriter;

import it.pa.unicam.stronatisamuele115894.model.Position;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PositionWriterTest {

    Position p1;
    Position p2;
    ModelWriter<Position> pw;


    private void createPositions(double x1, double y1, double x2, double y2) {
        p1 = new Position(x1,y1);
        p2 = new Position(x2,y2);
    }

    @BeforeEach
    private void allocate() {
        pw = new PositionWriter();
    }

    @AfterEach
    private void deallocate() {
        p1 = null;
        p2 = null;
        pw = null;
    }

    @Test
    public void checkString() {
        createPositions(2,3,3.5932,5.7883);
        assertEquals("2.0 3.0", pw.stringOf(p1));
        assertEquals("3.5932 5.7883", pw.stringOf(p2));
    }
}
