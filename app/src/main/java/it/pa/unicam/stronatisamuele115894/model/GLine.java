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

import javafx.scene.shape.LineTo;

/**
 * This class represents an immutable instance of a GeoSection, given the default
 * properties defined by Section abstract class. This instance is based on the immutable
 * condition that this Section must be associated to a straight line, which is represented
 * on a given field through the predefined JavaFX PathElement "LineTo", which is created at
 * construction time of this object and which are given as parameters the start position and
 * the end position of this Section instance.
 */

public final class GLine extends Section{

    /**
     * Constructor of a GLine, to represent a straight line on a field. This is
     * obtained by associating this section to the predefined JavaFX PathElement
     * "LineTo", which is created at construction time of this object and which are
     * given as parameters the start position and the end position of this Section instance.
     *
     * @param start       start position of the section;
     * @param end         end position of the section;
     * @param pensize     stroke width of the line drawn with this section;
     * @throws NullPointerException    if type, start, end or color argument is null;
     * @throws IllegalArgumentException if pensize is less than 1;
     */
    public GLine(Position start, Position end, int pensize)  {
        super(start, end, pensize, new LineTo(end.x(), end.y()));
    }

}
