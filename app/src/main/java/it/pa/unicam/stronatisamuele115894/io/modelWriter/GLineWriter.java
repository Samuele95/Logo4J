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

package it.pa.unicam.stronatisamuele115894.io.modelWriter;

import it.pa.unicam.stronatisamuele115894.model.GLine;

/**
 * This class is used to transform a 'GLine' element into a string.
 */

public class GLineWriter implements ModelWriter<GLine>{

    private final PositionWriter pw;
    private final RGBColorWriter cw;

    /**
     * Constructs an instance of this GLineWriter
     */
    public GLineWriter() {
        pw = new PositionWriter();
        cw = new RGBColorWriter();
    }

    /**
     * Returns the string representation of this GLine, as defined in
     * Section abstract base class. As compared to the base toString
     * implementation of the Section base class, this implementation
     * returns the line type too, which is "LINE". As an example, a
     * GLine created through this constructor
     *
     * new GLine(new Position(3,5),
     *           new Position(7,11),
     *           new RGBColor(47,47,47),
     *           1);
     *
     * will be printed as
     * LINE 3 5 7 11 47 47 47 1
     *
     * @return string representation of this GLine
     */
    @Override
    public String stringOf(GLine element) {
        return String.format("LINE %s %s %s %d",
                pw.stringOf(element.start()),
                pw.stringOf(element.end()),
                cw.stringOf(element.color().orElse(null)),
                element.pensize());
    }
}
