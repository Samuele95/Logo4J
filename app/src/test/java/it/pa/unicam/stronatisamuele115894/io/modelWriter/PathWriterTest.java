package it.pa.unicam.stronatisamuele115894.io.modelWriter;

import it.pa.unicam.stronatisamuele115894.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathWriterTest {

    ModelWriter<GeoPath> mw;

    @BeforeEach
    private void allocate() {
        mw = new PathWriter<>();
    }

    @AfterEach
    private void deallocate() {
        mw = null;
    }

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
    public void checkString() throws IllegalAccessException {
        assertEquals(String.format("LINE 5.0 5.0 5.0 8.0 0 0 0 2%n" +
                "LINE 5.0 8.0 7.25 9.43 255 255 255 2%n" +
                "LINE 7.25 9.43 2.5 2.5 47 47 47 2%n"),mw.stringOf(createPathWithThreeSections()));
        assertEquals(String.format("POLYGON 3 47 47 47%n" +
                "5.0 5.0 0 0 0 2%n" +
                "5.0 8.0 255 255 255 2%n" +
                "7.25 9.43 47 47 47 2%n"),mw.stringOf(createPolygonWithThreeSections()));
    }

}
