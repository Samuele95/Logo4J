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


import javafx.scene.layout.Pane;

import java.util.List;

/**
 * This interface represents a 2D cartesian coordinate field used to draw
 * shapes and paths on the screen. Every field is defined through the two-dimensional
 * "double" parameters "height" and "width", together with a background color. Every
 * implementing class defines its own range of values for the "x" and "y" axis.
 */

public interface Field {

    /**
     * Returns the height of the field.
     * @return height of the field.
     */
    double getHeight();

    /**
     * Returns the width of the field.
     * @return width of the field.
     */
    double getWidth();



    /**
     * Returns the background color of the field, as per
     * the RGB color model.
     * @return RGB color of the field.
     */
    RGBColor getFill();

    RGBColor getDefaultFill();

    /**
     * Sets a new background color for the field, as per
     * the RGB color model.
     *
     * @param fill new RGB color for the field.
     */
    String setScreenColor(RGBColor fill);

    List<GeoPath> getPaths();

    GeoPath getDrawingPath();

    GeoPath getDefaultPath();

    boolean createPath(GeoPath path, boolean setDefault);

    default boolean createPath(GeoPath path) {return createPath(path,false);}

    default boolean createPath() {return createPath(getDefaultPath());}

    boolean draw(GeoSection section, RGBColor stroke, RGBColor fill) throws IllegalAccessException;

    /**
     * Clears the field, removing every element from it.
     *
     * @return
     */
    String clearScreen();

    /**
     * Checks whether a given position is within field's bounds.
     * @param position the given position
     * @return true if the position is inside the field, false otherwise.
     */
    boolean isWithinBounds(Position position);
}
