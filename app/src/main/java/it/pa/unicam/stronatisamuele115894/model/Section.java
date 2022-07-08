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

import javafx.scene.shape.PathElement;
import java.util.Objects;
import java.util.Optional;


/**
 * This class represents abstract common properties for a default implementation of
 * the GeoSection interface, which is associated to a geometrical section drawn on
 * a field. Every section  is defined by a line of various form on the field which
 * goes from a given starting position to an end position. Every line has an associated
 * RGB-model color value and another value associate to the stroke width. Every instance
 * must be immutable.
 */

public abstract class Section implements GeoSection {

    private final Position start;
    private final Position end;
    private final int pensize;
    private final PathElement pathElement;
    private RGBColor color;
    /**
     * Constructor of a section, to represent a geometrical line on a field.
     *
     * @param start start position of the section;
     * @param end end position of the section;
     * @param pensize stroke width of the line drawn with this section;
     * @param pathElement JavaFX PathElement instance to draw this section on a field.
     *
     * @throws NullPointerException
     *          if type, start, end or color argument is null;
     * @throws IllegalArgumentException
     *          if pensize is less than 1;
     */

    public Section(Position start, Position end, int pensize, PathElement pathElement)  {
        if (pensize < 1)
            throw new IllegalArgumentException("Pensize must be at least of value 1");
        this.start = Objects.requireNonNull(start, "A section must have a start position");
        this.end = Objects.requireNonNull(end, "A section must have an end position");
        this.pensize = pensize;
        this.pathElement = Objects.requireNonNull(pathElement);
        this.color = null;
    }

    /**
     * Start position of this section on the field
     * @return start position
     */
    @Override
    public Position start() {
        return start;
    }

    /**
     * End position of this section on the field
     * @return end position
     */
    @Override
    public Position end() {
        return end;
    }

    /**
     * RGBColor associated to this section. If null,
     * the color associated to this section is "transparent",
     * which means no actual line would be drawn on
     * screen.
     * @return RGBColor associated to this section, or null
     */
    @Override
    public Optional<RGBColor> color() {
        return Optional.ofNullable(color);
    }

    /**
     * Stroke size of this section
     * @return stroke size
     */
    @Override
    public int pensize() {
        return pensize;
    }

    /**
     * JavaFX PathElement instance to draw this section
     * on a given field. It gives the necessary geometrical
     * shape to this section instance.
     * @return JavaFX PathElement instance
     */
    @Override
    public PathElement pathElement() {
        return pathElement;
    }

    /**
     * Set the RGBColor associated to this section. If null,
     * the color associated to this section is "transparent",
     * which means no actual line would be drawn on
     * screen.
     *
     * @param stroke the RGBColor associated to this section.
     *               May be null.
     */
    @Override
    public void setColor(RGBColor stroke) throws IllegalAccessException {
        if (this.color != null)
            throw new IllegalAccessException("You can't modify the color of a section if it is already set!");
        this.color = stroke;
    }

    /**
     * Returns the string representation of this section.
     * The string consists of the section type as defined in SectionType
     * enum, together with the "x y" pair of the start position and the
     * "x y" pair of the end position, followed by the string representation
     * of an RGBColor class element and the integer value associated to stroke
     * width.
     *
     * @return string representation of section.
     */

    @Override
    public String toString() {
        String colorString = (Objects.isNull(color)) ? "N N N" : String.format("%d %d %d", color.red(),color.green(), color.blue());
        return String.format("%s instance created:%n" +
                "Start position: %s%n" +
                "End position: %s%n" +
                "Pensize: %d%n" +
                "Color: %s%n" +
                "PathElement: %s%n", this.getClass().getSimpleName(), start,end,pensize,colorString,pathElement.getClass().getSimpleName());
    }

}