package it.pa.unicam.stronatisamuele115894.io.modelWriter;

import it.pa.unicam.stronatisamuele115894.model.GLine;
import it.pa.unicam.stronatisamuele115894.model.Position;
import it.pa.unicam.stronatisamuele115894.model.RGBColor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GLineWriterTest {

    GLine s1;
    GLine s2;
    ModelWriter<GLine> mw;

    @BeforeEach
    private void allocate() {
        s1 = new GLine(
                new Position(3,5),
                new Position(7,11),
                1);
        s2 = new GLine(
                new Position(3.5325,5.135265),
                new Position(7.6543345,11.753687),
                3);
        mw = new GLineWriter();
    }

    @AfterEach
    private void deallocate() {
        s1 = null;
        s2 = null;
        mw = null;
    }


    @Test
    public void checkString() throws IllegalAccessException {
        assertEquals(mw.stringOf(s1), "LINE 3.0 5.0 7.0 11.0 N N N 1");
        s1.setColor(new RGBColor(5,5,5));
        assertEquals(mw.stringOf(s1), "LINE 3.0 5.0 7.0 11.0 5 5 5 1");
        assertEquals(mw.stringOf(s2), "LINE 3.5325 5.135265 7.6543345 11.753687 N N N 3");
        s2.setColor(new RGBColor(42,64,8));
        assertEquals(mw.stringOf(s2), "LINE 3.5325 5.135265 7.6543345 11.753687 42 64 8 3");
    }
}
