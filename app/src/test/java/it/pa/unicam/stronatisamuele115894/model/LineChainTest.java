package it.pa.unicam.stronatisamuele115894.model;


import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import java.util.ArrayList;


public class LineChainTest {

    private boolean addSectionToPath(GeoPath path, GeoSection section, RGBColor color) throws IllegalAccessException {
        section.setColor(color);
        return path.add(section);
    }

    private GeoPath createPathWithThreeSections() throws IllegalAccessException {
        GeoPath path = new LineChain(new ArrayList<>(), new RGBColor(0,0,0));
        assertTrue(path.start().isEmpty());
        assertTrue(addSectionToPath(path, new GLine(new Position(5,5),new Position (5,8),2), new RGBColor(0,0,0)));
        assertTrue(addSectionToPath(path, new GLine(new Position(5,8),new Position (7.25,9.43),2), new RGBColor(255,255,255)));
        assertTrue(addSectionToPath(path, new GLine(new Position(7.25,9.43),new Position (2.5,2.5),2), new RGBColor(47,47,47)));
        assertTrue(path.stroke().isPresent());
        assertFalse(path.isClosed());
        return path;
    }

    private GeoPath createPolygonWithThreeSections() throws IllegalAccessException {
        GeoPath path = new LineChain(new ArrayList<>(), new RGBColor(0,0,0));
        assertTrue(path.start().isEmpty());
        assertTrue(addSectionToPath(path, new GLine(new Position(5,5),new Position (5,8),2), new RGBColor(0,0,0)));
        assertTrue(addSectionToPath(path, new GLine(new Position(5,8),new Position (7.25,9.43),2), new RGBColor(255,255,255)));
        assertTrue(addSectionToPath(path, new GLine(new Position(7.25,9.43),new Position (5,5),2), new RGBColor(47,47,47)));
        path.setFill(new RGBColor(47,47,47));
        assertTrue(path.stroke().isPresent());
        assertTrue(path.isClosed());
        return path;
    }

    @Test
    public void checkIfNullPointerExceptionIsThrown() {
        assertThrows(NullPointerException.class,
                () -> new LineChain(null,
                        null));
        assertThrows(NullPointerException.class,
                () -> new LineChain(null,
                        new RGBColor(0,0,0)));
        GeoPath p1 = new LineChain(new ArrayList<>(),
                null);
        assertThrows(NullPointerException.class,
                () -> p1.add(null));
    }

    @Test
    public void checkIfIllegalArgumentExceptionIsThrown() throws IllegalAccessException {
        GeoPath p1 = new LineChain(new ArrayList<>(),new RGBColor(0,0,0));
        addSectionToPath(p1, new GLine(new Position(5,5),new Position (5,8),2), new RGBColor(0,0,0));
        assertThrows(IllegalArgumentException.class,
                () -> addSectionToPath(p1, new GLine(new Position(6.6,11),new Position (7,7),2), new RGBColor(0,0,0)));
    }

    @Test
    public void start() throws IllegalAccessException {
        GeoPath p1 = createPathWithThreeSections();
        assertTrue(p1.start().isPresent());
        assertEquals(new Position(5,5), p1.start().get());
    }

    @Test
    public void end() throws IllegalAccessException {
        GeoPath p1 = createPathWithThreeSections();
        assertTrue(p1.end().isPresent());
        assertEquals(new Position(2.5,2.5), p1.end().get());
    }

    @Test
    public void checkIfElementsAreNotAdded() throws IllegalAccessException {
        GeoPath p1 = createPathWithThreeSections();
        assertEquals(3,p1.getPathSections().size());
        assertFalse(p1.add(new GLine(new Position(2.5,2.5), new Position(7,7), 2)));
        assertEquals(3,p1.getPathSections().size());
        GeoPath p2 = createPolygonWithThreeSections();
        assertEquals(3,p2.getPathSections().size());
        assertFalse(p2.add(new GLine(new Position(5,5), new Position(7,7), 2)));
        assertEquals(3,p2.getPathSections().size());
    }

    @Test
    public void shouldBeClosed() throws IllegalAccessException {
        createPolygonWithThreeSections();
    }



    @Test
    public void checkString() throws IllegalAccessException {
        assertEquals("Path of 3 sections", createPathWithThreeSections().toString());
        assertEquals("Path of 3 sections", createPolygonWithThreeSections().toString());
    }
}
