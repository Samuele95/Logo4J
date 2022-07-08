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

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a geometrical path tracked by a given cursor.
 * over a cartesian coordinate field. Every path is defined by a type, and
 * it can be made up of a series of sections so that the path itself is
 * regarded as a "polyline". Default implementations of this interface construct
 * "polyline" types of path. A path can also be considered as made up of a single
 * section,being regarded as a "shape" consequently. Every component section can
 * be defined by a different geometrical shape, represented by a "SectionType"
 * and an associated "PathElement". Every geometrical path is associated to only
 * one given cursor.
 *
 * Adding a section to a GeoPath is possible through the "add" method, which
 * takes a "SectionType" as arguments. Two paths can never be equal, according
 * to the "equals" method. This means that,whether two paths share the same
 * instance fields, they still are different and the most recent one overrides the older.
 */

public interface GeoPath {

    /**
     * Returns an Optional associated to the start position of this path
     * @return an Optional instance of the start position, if present;
     *         an empty Optional otherwise
     */
    Optional<Position> start();

    /**
     * Returns an Optional associated to the end position of this path
     * @return an Optional instance of the end position, if present;
     *         an empty Optional otherwise
     */
    Optional<Position> end();

    /**
     * Returns the color used to fill a close area eventually drawn
     * by this path. It may be null. If null, the color is portrayed
     * as being "transparent" on a GUI-Styled application.
     * @return fill color
     */
    RGBColor fill();

    /**
     * Returns the Optional of the color used to draw the stroke
     * associated to this path over a certain field. Stroke color
     * must always be set during instantiation of a GeoPath.
     * @return an Optional instance of the stroke color, if present;
     *         an empty Optional otherwise
     */
    Optional<RGBColor> stroke();


    /**
     * Returns a list of all the GeoSection instances this GeoPath
     * is composed of.
     * @return list of GeoSections instances
     */
    List<? extends GeoSection> getPathSections();

    /**
     * Returns true if this GeoPath instance is closed, false otherwise.
     * A GeoPath instance is closed if its start position equals its
     * end position.
     * @return true if this GeoPath instance is closed, false otherwise.
     */
    boolean isClosed();

    /**
     * Adds the given GeoSection instance to this path
     * @param section a GeoSection instance
     * @return true if the GeoSection is correctly added to the path,
     *         false otherwise;
     * @param <S> Specific type of the GeoSection instance. Must be a
     *            GeoSection subtype.
     */
    <S extends GeoSection> boolean add(S section);

    /**
     * Sets the color used to fill a close area eventually drawn
     * by this path. It may be null. If null, the color is portrayed
     * as being "transparent" on a GUI-Styled application.
     * @param fill color used to fill a close area eventually drawn
     *             by this path
     * @return fill color
     */
    RGBColor setFill(RGBColor fill);


    /**
     * Sets the color used to fill a close area eventually drawn
     * by this path. It may be null. If null, the color is portrayed
     * as being "transparent" on a GUI-Styled application.
     * @param newStroke color used to fill a close area eventually drawn
     *                  by this path
     * @return stroke color
     */
    RGBColor setStroke(RGBColor newStroke);

    /**
     * Returns a deep copy of this GeoPath instance.
     * @return deep copy of this GeoPath instance.
     */
    GeoPath makeCopy();
}
