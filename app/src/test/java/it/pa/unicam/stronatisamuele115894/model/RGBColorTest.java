package it.pa.unicam.stronatisamuele115894.model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class RGBColorTest {

    private List<Integer> colorComponents;

    private RGBColor staticConstructor(Integer ... args) {
        colorComponents = new ArrayList<>();
        Arrays.stream(args).sequential().forEach(arg -> colorComponents.add(arg));
        return RGBColor.create(colorComponents);
    }

    @Test
    public void checkIfExceptionsAreThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> new RGBColor(0,0,259),
                "RGB values must be in the range [0,255]");
        assertThrows(IllegalArgumentException.class,
                () -> new RGBColor(-3,0,255),
                "RGB values must be in the range [0,255]");
    }

    @Test
    public void checkToString() {
        assertEquals(new RGBColor(0,0,0).toString(),
                String.format("RGBColor instance:%n" +
                                "Red component: 0%n" +
                                "Green component: 0%n" +
                                "Blue component: 0%n"));
        assertEquals(new RGBColor(45, 132, 254).toString(),
                String.format("RGBColor instance:%n" +
                        "Red component: 45%n" +
                        "Green component: 132%n" +
                        "Blue component: 254%n"));
    }

    @Test
    public void checkStaticConstructor() {
        assertEquals(staticConstructor(255,45,47).toString(),
                String.format("RGBColor instance:%n" +
                        "Red component: 255%n" +
                        "Green component: 45%n" +
                        "Blue component: 47%n"));
        assertThrows(IllegalArgumentException.class,
                () -> staticConstructor(255,45,47,21),
                "An RGBColor must have exactly three components!");
    }
}
