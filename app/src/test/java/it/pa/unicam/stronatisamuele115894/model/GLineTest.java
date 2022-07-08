package it.pa.unicam.stronatisamuele115894.model;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GLineTest {

    GeoSection s1;
    GeoSection s2;

    @BeforeEach
    private void initiateGLines() {
        s1 = new GLine(
                new Position(3,5),
                new Position(7,11),
                1);
        s2 = new GLine(
                new Position(3.5325,5.135265),
                new Position(7.6543345,11.753687),
                3);
    }

    @AfterEach
    private void deallocateGLines() {
        s1 = null;
        s2 = null;
    }

    @Test
    public void checkifIllegalArgumentExceptionisThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> new GLine(
                        new Position(5.653, 5.7895),
                        new Position(7.346, 11.42),
                        0),
                "Pensize must be at least of value 1"
        );
    }

    @Test
    public void checkifNullPointerExceptionisThrown() {
        assertThrows(NullPointerException.class,
                () -> new GLine(
                        null,
                        new Position(7, 11),
                        1),
                "A section must have a start position"
        );
    }



    @Test
    public void color() throws IllegalAccessException {
        assertTrue(s1.color().isEmpty());
        s1.setColor(new RGBColor(5,5,5));
        assertTrue(s1.color().isPresent());
        assertEquals(s1.color().get().toString(),String.format("RGBColor instance:%n" +
                "Red component: 5%n" +
                "Green component: 5%n" +
                "Blue component: 5%n"));
        assertThrows(IllegalAccessException.class,
                () -> s1.setColor(new RGBColor(5,5,5)),
                "You can't modify the color of a section if it is already set!");
    }

    @Test
    public void checkToString() throws IllegalAccessException {
        assertEquals(s1.toString(),String.format("GLine instance created:%n" +
                "Start position: Position instance: x=3.0 y=5.0%n" +
                "End position: Position instance: x=7.0 y=11.0%n" +
                "Pensize: 1%n" +
                "Color: N N N%n" +
                "PathElement: LineTo%n"));
        s1.setColor(new RGBColor(5,5,5));
        assertEquals(s1.toString(),String.format("GLine instance created:%n" +
                "Start position: Position instance: x=3.0 y=5.0%n" +
                "End position: Position instance: x=7.0 y=11.0%n" +
                "Pensize: 1%n" +
                "Color: 5 5 5%n" +
                "PathElement: LineTo%n"));
    }

    @Test
    public void shouldBeDifferent() {
        assertNotEquals(new GLine(
                new Position(3,5),
                new Position(7,11),
                1), s1);
        assertNotEquals(new GLine(
                new Position(3.5325,5.135265),
                new Position(7.6543345,11.753687),
                3), s2);
    }
}
