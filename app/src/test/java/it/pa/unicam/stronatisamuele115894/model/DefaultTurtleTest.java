package it.pa.unicam.stronatisamuele115894.model;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultTurtleTest {

    Turtle turtle;

    Field field;

    Position startPosition;

    RGBColor stroke;

    RGBColor fill;

    @BeforeEach
    public void initializeTurtleArgs() {
        stroke = new RGBColor(0,0,0);
        fill = new RGBColor(255,255,255);
        field = new SquareField(200, 200, fill, new ArrayList<>(),
                new LineChain(new ArrayList<>(), new RGBColor(0,0,0)));
        startPosition = new Position(field.getWidth()/2, field.getHeight()/2 );
    }

    private void initializeTurtle() {
        turtle = new DefaultTurtle(field, startPosition,stroke,fill,1);
    }

    @AfterEach
    public void deallocateTurtle() {
        turtle = null;
        field= null;
        startPosition = null;
        stroke = null;
        fill = null;
    }

    @Test
    public void checkIfNullPointerExceptionIsThrown() {
        assertThrows(NullPointerException.class,
                () -> new DefaultTurtle(null, startPosition,stroke,fill,1),
                "Turtle must be in a field!");
        assertThrows(NullPointerException.class,
                () -> new DefaultTurtle(field, null,stroke,fill,1),
                "Position can't be null!");
        assertThrows(NullPointerException.class,
                () -> new DefaultTurtle(field, startPosition,null,fill,1),
                "This turtle must have a default stroke color!");
        assertThrows(NullPointerException.class,
                () -> new DefaultTurtle(field, startPosition,stroke,null,1),
                "This turtle should draw an area of a given color");
        assertThrows(NullPointerException.class,
                () -> new DefaultTurtle(field,startPosition,null,fill,1),
                "Turtle must be initialized with a stroke color!");
    }


    @Test
    public void checkNewPositions() {
        initializeTurtle();
        assertThrows(IllegalArgumentException.class,
                () -> turtle.setPosition(new Position(-50,-50)),
                "The turtle must stay inside its field!");
        assertThrows(IllegalArgumentException.class,
                () -> turtle.setPosition(new Position(55663,567332)),
                "The turtle must stay inside its field!");
        assertThrows(IllegalArgumentException.class,
                () -> turtle.setPosition(new Position(55663,567332)),
                "The turtle must stay inside its field!");
    }

    @Test
    public void checkTurtleMovement() throws IllegalAccessException {
        initializeTurtle();
        turtle.move(new Position(15,15),new GLine(turtle.getPosition(), new Position(15,15),1));
        turtle.move(new Position(25,35), new GLine(turtle.getPosition(), new Position(25,35),1));
        assertEquals(1, field.getPaths().size());
        assertEquals(2, field.getPaths().get(0).getPathSections().size());
        turtle.setPlot(false);
        turtle.move(new Position(56,23), new GLine(turtle.getPosition(), new Position(56,23), 1));
        assertEquals(2, field.getPaths().get(0).getPathSections().size());
        assertEquals(1, field.getPaths().get(1).getPathSections().size());
        assertTrue(field.getDrawingPath().stroke().isEmpty());
        turtle.setPlot(true);
        turtle.move(new Position(11,11), new GLine(turtle.getPosition(), new Position(11,11), 1));
        assertEquals(1, field.getPaths().get(2).getPathSections().size());
        assertTrue(field.getDrawingPath().stroke().isPresent());
    }


    @Test
    public void checkIfPolygonIsDrawn() throws IllegalAccessException {
        initializeTurtle();
        turtle.move(new Position(2,2), new GLine(turtle.getPosition(), new Position(2,2),1));
        turtle.move(new Position(5,8), new GLine(turtle.getPosition(), new Position(5,8),1));
        turtle.move(startPosition, new GLine(turtle.getPosition(), startPosition,1));
        assertEquals(1, field.getPaths().size());
        assertEquals(3, field.getPaths().get(0).getPathSections().size());
        assertTrue(field.getPaths().get(0).isClosed());
    }

    @Test
    public void checkIfPolygonIsDrawnAfterRandomPath() throws IllegalAccessException {
        initializeTurtle();
        turtle.move(new Position(2,2), new GLine(turtle.getPosition(), new Position(2,2),1));
        turtle.setPlot(false);
        turtle.move(new Position(5,8), new GLine(turtle.getPosition(), new Position(5,8),1));
        turtle.setPlot(true);
        turtle.move(new Position(12,12), new GLine(turtle.getPosition(), new Position(12,12),1));
        turtle.move(new Position(15,15), new GLine(turtle.getPosition(), new Position(15,15),1));
        turtle.move(new Position(18,18), new GLine(turtle.getPosition(), new Position(18,18),1));
        turtle.move(new Position(5,8), new GLine(turtle.getPosition(), new Position(5,8),1));
        assertEquals(3, field.getPaths().size());
        assertEquals(4, field.getPaths().get(2).getPathSections().size());
        assertTrue(field.getPaths().get(2).isClosed());
    }

    @Test
    public void checkIfPolygonIsNotDrawn() throws IllegalAccessException {
        initializeTurtle();
        turtle.move(new Position(2,2), new GLine(turtle.getPosition(), new Position(2,2),1));
        turtle.setPlot(false);
        turtle.move(new Position(5,8), new GLine(turtle.getPosition(), new Position(5,8),1));
        turtle.setPlot(true);
        turtle.move(new Position(12,12), new GLine(turtle.getPosition(), new Position(12,12),1));
        turtle.setPlot(false);
        turtle.move(new Position(15,15), new GLine(turtle.getPosition(), new Position(15,15),1));
        turtle.setPlot(true);
        turtle.move(new Position(18,18), new GLine(turtle.getPosition(), new Position(18,18),1));
        turtle.move(new Position(5,8), new GLine(turtle.getPosition(), new Position(5,8),1));
        assertEquals(5, field.getPaths().size());
        assertEquals(2, field.getPaths().get(4).getPathSections().size());
        assertFalse(field.getPaths().get(4).isClosed());
    }
}
