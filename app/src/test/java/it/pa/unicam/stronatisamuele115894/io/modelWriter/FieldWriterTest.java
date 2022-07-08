package it.pa.unicam.stronatisamuele115894.io.modelWriter;

import it.pa.unicam.stronatisamuele115894.model.Field;
import it.pa.unicam.stronatisamuele115894.model.LineChain;
import it.pa.unicam.stronatisamuele115894.model.RGBColor;
import it.pa.unicam.stronatisamuele115894.model.SquareField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class FieldWriterTest {

    Field field;
    ModelWriter<Field> mw;

    @BeforeEach
    private void createField() {
        field = new SquareField(
                200,
                200,
                new RGBColor(255, 255, 255),
                new ArrayList<>(),
                new LineChain(new ArrayList<>(),new RGBColor(0,0,0)));
        mw = new FieldWriter();
    }

    @AfterEach
    private void deallocate() {
        field = null;
    }
}