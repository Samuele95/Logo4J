package it.pa.unicam.stronatisamuele115894.model;


import java.util.Objects;

/**
 * This class defines a default implementation of a
 * Turtle.
 */

public class DefaultTurtle implements Turtle {

    private Position position;
    private int direction;
    private RGBColor stroke;
    private final RGBColor defaultStroke;
    private RGBColor fill;
    private boolean plot;
    private final Field field;
    private int pensize;


    /**
     * Constructor of a turtle instance.
     * @param f Field on which the turtle is positioned
     * @param startPosition start Position of the turtle
     * @param stroke stroke color
     * @param fill fill color of an area eventually drawn by the turtle
     * @param pensize stroke size
     */

    public DefaultTurtle(Field f, Position startPosition, RGBColor stroke, RGBColor fill, int pensize) {
        this.field = Objects.requireNonNull(f, "Turtle must be in a field!");
        this.stroke = Objects.requireNonNull(stroke, "Turtle must be initialized with a stroke color!");
        this.defaultStroke = stroke;
        setPosition(startPosition);
        this.fill = Objects.requireNonNull(fill, "You can't set a null fill!");
        this.pensize = pensize;
        this.plot = true;
        this.direction = 0;
    }

    /**
     * Returns the actual position of this turtle.
     * @return actual position of this turtle.
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Returns the actual direction of this turtle.
     * @return actual direction of this turtle.
     */
    @Override
    public int getDirection() {
        return direction;
    }

    /**
     * Returns the color of the path drawn by this turtle.
     * @return color of the path drawn by this turtle.
     */
    @Override
    public RGBColor getStroke() {
        return stroke;
    }

    /**
     * Returns the color of the area drawn by this turtle.
     * @return color of the area drawn by this turtle.
     */
    @Override
    public RGBColor getFill() {
        return fill;
    }

    /**
     * Returns the size of the stroke drawn by this turtle.
     * @return stroke size.
     */
    @Override
    public int getPenSize() {
        return pensize;
    }

    /**
     * Returns true if the turtle is allowed to draw a path,
     * false otherwise.
     * @return true if the turtle is allowed to draw a path,
     *         false otherwise.
     */
    @Override
    public boolean getPlot() {
        return plot;
    }

    /**
     * Returns the coordinate field on which this turtle draws a path.
     * @return coordinate field on which this turtle draws a path.
     */
    @Override
    public Field getField() {
        return field;
    }

    /**
     * Set this turtle to a new position, passed as argument.
     *
     * @param newPosition the new position of this turtle.
     * @throws IllegalArgumentException if this position is not
     *                                  within the bounds of the given field.
     * @throws NullPointerException     if the new position is null.
     */
    @Override
    public String setPosition(Position newPosition) {
        if (!field.isWithinBounds(newPosition))
            throw new IllegalArgumentException("The turtle must stay inside its field!");
        position = newPosition;
        return String.format("The turtle has changed its position. It is now at (x:%.1f y:%.1f)%n",
                newPosition.x(),newPosition.y());
    }

    /**
     * Set this turtle to a new position, computed by the two integer
     * arguments given. The new position is computed through the function
     * defined by "computeNewPosition" method.
     * @param par1 first argument of the function;
     * @param par2 second argument of the function;
     * @return string representing the result of the activity.
     */
    @Override
    public String setPosition(int par1, int par2) {
        return setPosition(computeNewPosition(par1,par2));
    }


    /**
     * Clears the field on which this turtle is positioned.
     * @return string representing the result of the activity.
     */
    @Override
    public String clearScreen() {
        this.field.clearScreen();
        setPosition(new Position(field.getWidth()/2,field.getHeight()/2));
        return String.format("New position%n");
    }

    /**
     * Moves this turtle on a new position on the field, moving
     * through the given GeoSection instance. For this reason, the
     * section end point must be equal to the new Position instance.
     * @param newPosition new position of the turtle
     * @param section section describing the path followed by the turtle
     * @return string representing the result of the activity.
     * @throws IllegalArgumentException if the section end point is different from the turtle new position
     *                                  or the section color is different from turtle's stroke color
     */
    @Override
    public String move(Position newPosition, GeoSection section){
        try {
            setPosition(newPosition);
            if (!Objects.equals(section.end(), newPosition))
                throw new IllegalArgumentException("Turtle has to move to its new position");
            field.draw(Objects.requireNonNull(section), getStroke(), getFill());
            return String.format("Moving this turtle to position (%s) through following section: %s%n", newPosition, section);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Turtle stroke color and section stroke must be equal!");
        }
    }

    /**
     * Moves this turtle on a new position on the field, moving
     * through the given GeoSection instance. For this reason, the
     * section end point must be equal to the new Position instance.
     * The new position is computed by the two integer arguments given.
     * through the function defined by "computeNewPosition" method.
     * @param par1 first argument of the function;
     * @param par2 second argument of the function;
     * @param section section describing the path followed by the turtle
     * @return string representing the result of the activity.
     * @param <S> GeoSection type
     * @throws IllegalArgumentException if the section end point is different from the turtle new position
     *                                  or the section color is different from turtle's stroke color
     */
    @Override
    public <S extends GeoSection> String move(int par1, int par2, S section) {
        return move(computeNewPosition(par1,par2),section);
    }

    /**
     * Set the new direction of this turtle. The direction is represented
     * as an integer parameter whose value is in the range [0,360].
     * <p>
     * From an implementation point of view,the newAngle value should be an
     * integer value computed as (newAngle % 360), which ensures its
     * compatibility to the given preconditions.
     *
     * @param newAngle new direction of this turtle.
     * @return string representing the result of the activity.
     */
    @Override
    public String setDirection(int newAngle) {
        direction = newAngle % 360;
        return String.format("Setting turtle direction to %d",direction);
    }

    /**
     * Sets the color of the path drawn by this turtle.
     *
     * @param stroke color of the path drawn by this turtle.
     * @return string representing the result of the activity.
     */
    @Override
    public String setStroke(RGBColor stroke) {
        this.stroke = stroke;
        return String.format("Setting stroke color to new RGBColor(%s)",stroke);
    }

    /**
     * Sets the color of the area drawn by this turtle.
     *
     * @param fill color of the area drawn by this turtle.
     * @return string representing the result of the activity.
     */
    @Override
    public String setFill(RGBColor fill) {
        this.fill = Objects.requireNonNull(fill,
                "You can't set a null fill!");
        return (String.format("Setting fill color to new RGBColor(%s)",fill));
    }

    /**
     * Sets the flag which signals whether this turtle is
     * able to draw a line or not. If the "plot" flag is true,
     * this turtle is able to draw a line.
     *
     * @param flag signals whether this turtle ia able to draw
     *             a line or not.
     * @return string representing the result of the activity.
     */
    @Override
    public String setPlot(boolean flag) {
        this.stroke = (flag) ? defaultStroke : null;
        plot = flag;
        return String.format("Turtle can draw: %s",flag);
    }

    /**
     * Sets the size of the stroke drawn by this turtle.
     * @param pensize size of the stroke.
     * @return string representing the result of the activity.
     */
    @Override
    public String setPenSize(int pensize) {
        this.pensize = pensize;
        return String.format("Setting stroke size to %d",pensize);
    }

    /**
     * Computes a new Position instance through a defined function,
     * which takes two integers as arguments.
     * @param par1 first argument of the function;
     * @param par2 second argumento of the function
     * @return new Positio instance
     */
    @Override
    public Position computeNewPosition(int par1, int par2) {
        double rad = Math.toRadians(getDirection());
        double x = Math.round(getPosition().x() + par1 * Math.cos(rad));
        double y = Math.round(getPosition().y() + par2 * Math.sin(rad));
        Position newPosition;
        if (!getField().isWithinBounds(newPosition = new Position(x,y)))
            throw new IllegalArgumentException("This position is out of bounds!");
        return newPosition;
    }


    /**
     * String representation for logging purposes
     * @return logString of this turtle
     */
    @Override
    public String toString() {
        return String.format("Field: width:%.1f height:%.1f background color:%s%n",
                field.getWidth(), field.getHeight(), field.getFill()) +
                String.format("Actual position: (x:%.1f y:%.1f)%n",
                        getPosition().x(), getPosition().y()) +
                String.format("Stroke color: %s%n",
                        getStroke()) +
                String.format("Fill color: %s%n",
                        getFill()) +
                String.format("Pensize: %d%n",
                        pensize);
    }
}
