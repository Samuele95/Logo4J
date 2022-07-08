/*******************************************************************************
 * Copyright (c) 2022 Samuele Stronati, Università di Camerino.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contributors:
 *     Samuele Stronati - initial API and implementation
 *******************************************************************************/

package it.pa.unicam.stronatisamuele115894.model;

/**
 * This interface represents a cursor, named "Turtle", for a path in a cartesian coordinate
 * field. Every cursor is represented by a position in the cartesian coordinate field, together
 * with a direction (angle pointed by the cursor as compared to the cartesian coordinate field)
 * which is represented as an integer parameter whose value is in the range [0,360],a stroke color
 * (color of the path drawn by the cursor), a fill color (color of the area eventually drawn by
 * the cursor through a path) and a boolean parameter "plot", which is a flag used to signal whether
 * the cursor is actually able to draw a path or not. Two turtles can never be equal, according to
 * the "equals" method. This means that, whether two turtles share the same instance fields, they
 * still are different.
 */

public interface Turtle {

    /**
     * Returns the actual position of this turtle.
     * @return actual position of this turtle.
     */
    Position getPosition();

    /**
     * Returns the actual direction of this turtle.
     * @return actual direction of this turtle.
     */
    int getDirection();

    /**
     * Returns the color of the path drawn by this turtle.
     * @return color of the path drawn by this turtle.
     */
    RGBColor getStroke();

    /**
     * Returns the color of the area drawn by this turtle.
     * @return color of the area drawn by this turtle.
     */
    RGBColor getFill();

    /**
     * Returns the size of the stroke drawn by this turtle.
     * @return stroke size.
     */
    int getPenSize();

    /**
     * Returns true if the turtle is allowed to draw a path,
     * false otherwise.
     * @return true if the turtle is allowed to draw a path,
     *         false otherwise.
     */
    boolean getPlot();

    /**
     * Returns the coordinate field on which this turtle draws a path.
     * @return coordinate field on which this turtle draws a path.
     */
    <F extends Field> F getField();


    /**
     * Set this turtle to a new position, passed as argument.
     *
     * @param newPosition the new position of this turtle.
     * @throws IllegalArgumentException if this position is not
     *                                  within the bounds of the given field.
     * @throws NullPointerException     if the new position is null.
     */
    String setPosition(Position newPosition) throws IllegalArgumentException;

    /**
     * Set this turtle to a new position, computed by the two integer
     * arguments given. The new position is computed through the function
     * defined by "computeNewPosition" method.
     * @param par1 first argument of the function;
     * @param par2 second argument of the function;
     * @return string representing the result of the activity.
     */
    String setPosition(int par1, int par2);

    /**
     * Clears the field on which this turtle is positioned.
     * @return string representing the result of the activity.
     */
    String clearScreen();

    /**
     * Moves this turtle on a new position on the field, moving
     * through the given GeoSection instance. For this reason, the
     * section end point must be equal to the new Position instance.
     * @param newPosition new position of the turtle
     * @param section section describing the path followed by the turtle
     * @return string representing the result of the activity.
     * @param <S> GeoSection type
     * @throws IllegalArgumentException if the section end point is different from the turtle new position
     *                                  or the section color is different from turtle's stroke color
     */
    <S extends GeoSection> String move(Position newPosition, S section) throws IllegalArgumentException;


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
    <S extends GeoSection> String move(int par1, int par2, S section) throws IllegalArgumentException;

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
    String setDirection(int newAngle);

    /**
     * Sets the color of the path drawn by this turtle.
     *
     * @param stroke color of the path drawn by this turtle.
     * @return string representing the result of the activity.
     */
    String setStroke(RGBColor stroke);

    /**
     * Sets the color of the area drawn by this turtle.
     *
     * @param fill color of the area drawn by this turtle.
     * @return string representing the result of the activity.
     */
    String setFill(RGBColor fill);


    /**
     * Sets the flag which signals whether this turtle is
     * able to draw a line or not. If the "plot" flag is true,
     * this turtle is able to draw a line.
     *
     * @param flag signals whether this turtle ia able to draw
     *             a line or not.
     * @return string representing the result of the activity.
     */
    String setPlot(boolean flag);

    /**
     * Sets the size of the stroke drawn by this turtle.
     * @param pensize size of the stroke.
     * @return string representing the result of the activity.
     */
    String setPenSize(int pensize);

    /**
     * Computes a new Position instance through a defined function,
     * which takes two integers as arguments.
     * @param par1 first argument of the function;
     * @param par2 second argumento of the function
     * @return new PositioN instance
     */
    Position computeNewPosition(int par1, int par2);
}
