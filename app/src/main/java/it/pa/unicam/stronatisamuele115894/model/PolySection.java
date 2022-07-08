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



import java.util.*;


/**
 * This class gives a base abstract implementation of a GeoPath.
 */

public abstract class PolySection implements GeoPath {


    private final List<GeoSection> pathSections;
    private Position start;
    private Position end;
    private RGBColor fill;
    private RGBColor stroke;

    /**
     * Constructs a base PolySection with a list used to collect
     * all the GeoSections which may compose this PolySection,
     * together with a stroke color.
     * @param pathSections empty list of GeoSections
     * @param color stroke color
     * @throws NullPointerException
     *              if the given List is null
     */
    @SuppressWarnings("unchecked")
    public PolySection(List<? extends GeoSection> pathSections, RGBColor color) {
        this.pathSections = (List<GeoSection>) Objects.requireNonNull(pathSections);
        this.start = null;
        this.end = null;
        this.fill = color;
        this.stroke = color;
    }

    /**
     * Copy constructor of a given PolySection instance.
     *
     * It may be used as a basic copy method to implement
     * and override the "makeCopy" method by a PolySection
     * specific concrete instance.
     * @param obj a PolySection instance
     */
    public PolySection(PolySection obj) {
        this.pathSections = new ArrayList<>(obj.pathSections);
        this.fill = (obj.fill == null) ? setFill(null) : setFill(new RGBColor(obj.fill.red(), obj.fill.green(), obj.fill.blue()));
        this.stroke = (obj.stroke == null) ? setStroke(null) : setStroke(new RGBColor(obj.stroke.red(), obj.stroke.green(), obj.stroke.blue()));
    }

    /**
     * Returns a deep copy of this GeoPath instance.
     * @return deep copy of this GeoPath instance.
     */
    @Override
    public abstract GeoPath makeCopy();

    /**
     * Returns an Optional associated to the start position of this path
     * @return an Optional instance of the start position, if present;
     *         an empty Optional otherwise
     */
    @Override
    public Optional<Position> start() {
        try {
            return Optional.ofNullable(pathSections.get(0).start());
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns an Optional associated to the end position of this path
     * @return an Optional instance of the end position, if present;
     *         an empty Optional otherwise
     */
    @Override
    public Optional<Position> end() {
        try {
            return Optional.ofNullable(pathSections.get(pathSections.size()-1).end());
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns the color used to fill a close area eventually drawn
     * by this path. It may be null. If null, the color is portrayed
     * as being "transparent" on a GUI-Styled application.
     * @return fill color
     */
    @Override
    public RGBColor fill() {
        return this.fill;
    }

    /**
     * Returns the Optional of the color used to draw the stroke
     * associated to this path over a certain field. Stroke color
     * must always be set during instantiation of a GeoPath.
     * @return an Optional instance of the stroke color, if present;
     *         an empty Optional otherwise
     */
    public Optional<RGBColor> stroke() {
        return Optional.ofNullable(stroke);
    }



    /**
     * Returns a list of all the GeoSection instances this GeoPath
     * is composed of.
     * @return list of GeoSections instances
     */
    @Override
    public List<? extends GeoSection> getPathSections() {
        return pathSections;
    }

    /**
     * Returns true if this GeoPath instance is closed, false otherwise.
     * A GeoPath instance is closed if its start position equals its
     * end position.
     * @return true if this GeoPath instance is closed, false otherwise.
     */
    @Override
    public final boolean isClosed() {
        if (start().isPresent() && end().isPresent())
            return Objects.equals(start().get(),end().get());
        return false;
    }

    /**
     * Adds the given GeoSection instance to this path
     * @param section a GeoSection instance
     * @return true if the GeoSection is correctly added to the path,
     *         false otherwise;
     * @param <S> Specific type of the GeoSection instance. Must be a
     *            GeoSection subtype.
     */
    @Override
    public <S extends GeoSection> boolean add(S  section) {
        if (this.end().isPresent() && !Objects.equals(this.end().get(),section.start()))
            throw new IllegalArgumentException("Sections should be adjacent!");
        return addSectionIfPossible(section);
    }


    private <S extends GeoSection> boolean addSectionIfPossible(S section) {
        if (section.color().isEmpty() ^ this.stroke == null)
            return false;
        boolean result = this.pathSections.add(section);
        setStroke(section.color().orElse(null));
        return result;
    }


    /**
     * Sets the color used to fill a close area eventually drawn
     * by this path. It may be null. If null, the color is portrayed
     * as being "transparent" on a GUI-Styled application.
     * @param newFill color used to fill a close area eventually drawn
     *             by this path
     * @return fill color
     */
    @Override
    public RGBColor setFill(RGBColor newFill) {
        return this.fill = newFill;
    }

    /**
     * Sets the color used for the stroke associated to
     * this path. It may be null. If null, the color is portrayed
     * as being "transparent" on a GUI-Styled application.
     * @param newStroke color used for the stroke
     * @return stroke color
     */
    @Override
    public RGBColor setStroke(RGBColor newStroke) {
        return this.stroke = newStroke;
    }


    /**
     * Returns the base string representation of this PolySection.
     * The string consists of a series of the Sections which compose
     * this PolySection and which are contained in the list returned
     * by "getPathSections" method. Every Section string is printed
     * accordingly to its own "toString" method.
     *
     * Every class implementing this base abstract class may define
     * a more specific string representation, which may refer to the
     * PolySection instance specific type.
     *
     * @return string representation of PolySection.
     */
    @Override
    public String toString() {
        return String.format("Path of %d sections", getPathSections().size());
    }
}
