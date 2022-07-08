package it.pa.unicam.stronatisamuele115894.model;


import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class SquareFieldTest {

    Field field;

    @BeforeEach
    private void createField() {
        field = new SquareField(
                200,
                200,
                new RGBColor(255, 255, 255),
                new ArrayList<>(),
                new LineChain(new ArrayList<>(),new RGBColor(0,0,0)));
    }

    @AfterEach
    private void deallocate() {
        field = null;
    }

    private boolean addToField(GeoSection section) throws IllegalAccessException {
        return field.draw(section, new RGBColor(0,0,0),new RGBColor(0,0,0));
    }

    private boolean addEmptyToField(GeoSection section) throws IllegalArgumentException, IllegalAccessException {
        return field.draw(section, null,new RGBColor(0,0,0));
    }

    @Test
    public void checkIfNullPointerExceptionIsThrown() {
        assertThrows(NullPointerException.class,
                () -> new SquareField(
                        200,
                        200,
                        new RGBColor(255, 255, 255),
                        null,
                        new LineChain(new ArrayList<>(),null)),
                "Paths need to be drawn on the field!");
        assertThrows(NullPointerException.class,
                () -> new SquareField(
                        200,
                        200,
                        null,
                        new ArrayList<>(),
                        new LineChain(new ArrayList<>(),null)),
                "A field must have a background color!");
        Field f1 = new SquareField(
                200,
                200,
                new RGBColor(255, 255, 255),
                new ArrayList<>(),
                new LineChain(new ArrayList<>(),null));
        assertThrows(NullPointerException.class,
                () -> f1.isWithinBounds(null),
                "Position can't be null!");
    }

    @Test
    public void checkIfIllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> new SquareField(-5,
                        10,
                        new RGBColor(255, 255, 255),
                        new ArrayList<>(),
                        new LineChain(new ArrayList<>(),null)),
                "Axis ranges must be [0, infty[ !");
    }

    @Test
    public void isWithinBounds() {
        assertFalse(field.isWithinBounds(new Position(-1, 0)));
        assertFalse(field.isWithinBounds(new Position(-23, -15)));
        assertFalse(field.isWithinBounds(new Position(0, 201)));
        assertFalse(field.isWithinBounds(new Position(202, 422)));
        assertTrue(field.isWithinBounds(new Position(56, 78)));
        assertTrue(field.isWithinBounds(new Position(200, 200)));
    }

    @Test
    public void checkIfSectionsInPathAreAdjacent() throws IllegalAccessException {
        assertTrue(addToField(new GLine(
                new Position(2, 2),
                new Position(15, 18),
                1)));
        assertThrows(IllegalArgumentException.class,
                () -> addToField(new GLine(
                        new Position(42, 42),
                        new Position(5, 10),
                        1)),
                "Sections should be adjacent!");
    }


    @Test
    public void checkDraw() throws IllegalAccessException {
        assertTrue(addToField(new GLine(
                new Position(2, 2),
                new Position(15, 18),
                1)));
        assertTrue(addEmptyToField(new GLine(
                new Position(15, 18),
                new Position(16, 16),
                1)));
        assertTrue(addEmptyToField(new GLine(
                new Position(16, 16),
                new Position(17, 17),
                1)));
        assertTrue(addToField(new GLine(
                new Position(17, 17),
                new Position(18, 18),
                1)));
        assertTrue(addToField(new GLine(
                new Position(18, 18),
                new Position(58, 45),
                1)));
        assertEquals(3, field.getPaths().size());
        assertEquals(1, field.getPaths().get(0).getPathSections().size());
        assertEquals(2, field.getPaths().get(1).getPathSections().size());
        assertEquals(2, field.getPaths().get(2).getPathSections().size());
    }


    @Test
    public void clearScreen() throws IllegalAccessException {
        checkDraw();
        field.clearScreen();
        assertEquals(1, field.getPaths().size());
        assertEquals(0, field.getPaths().get(0).getPathSections().size());
    }
}
