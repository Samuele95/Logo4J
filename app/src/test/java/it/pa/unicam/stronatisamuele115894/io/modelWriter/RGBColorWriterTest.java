package it.pa.unicam.stronatisamuele115894.io.modelWriter;


import it.pa.unicam.stronatisamuele115894.model.RGBColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RGBColorWriterTest {

    RGBColor c1;
    RGBColor c2;
    ModelWriter<RGBColor> mw;


    @BeforeEach
    private void allocate() {
        c1 = new RGBColor(0,0,0);
        c2 = new RGBColor(45,132,254);
        mw = new RGBColorWriter();
    }

    @AfterEach
    private void deallocate() {
        c1 = null;
        c2 = null;
        mw = null;
    }

    @Test
    public void checkString() {
        assertEquals("0 0 0", mw.stringOf(c1));
        assertEquals("45 132 254", mw.stringOf(c2));
    }
}
