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
import java.util.Optional;

/**
 * This interface represents a geometrical section drawn on a field. Every section
 * is defined by a line of various form on the field which goes from a given starting
 * position to an end position. Every line has an associated RGB-model color value
 * and another value associate to the stroke width. Every instance must be immutable.
 */

public interface GeoSection {


    /**
     * Start position of this section on the field
     * @return start position
     */
    Position start();

    /**
     * End position of this section on the field
     * @return end position
     */
    Position end();

    /**
     * RGBColor associated to this section. If null,
     * the color associated to this section is "transparent",
     * which means no actual line would be drawn on
     * screen.
     * @return RGBColor associated to this section, or null
     */
    Optional<RGBColor> color();

    /**
     * Stroke size of this section
     * @return stroke size
     */
    int pensize();

    /**
     * JavaFX PathElement instance to draw this section
     * on a given field. It gives the necessary geometrical
     * shape to this section instance.
     * @return JavaFX PathElement instance
     */
    PathElement pathElement();

    /**
     * Set the RGBColor associated to this section. If null,
     * the color associated to this section is "transparent",
     * which means no actual line would be drawn on
     * screen.
     *
     * @param stroke the RGBColor associated to this section.
     *               May be null.
     */
    void setColor(RGBColor stroke) throws IllegalAccessException;
}
