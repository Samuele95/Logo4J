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
import it.pa.unicam.stronatisamuele115894.model.GeoPath;
import it.pa.unicam.stronatisamuele115894.model.LineChain;

/**
 * This class is used to transform a 'GeoPath' element into a string.
 */

public class PathWriter<P extends GeoPath> implements ModelWriter<P>{

    private final GLineWriter glw;
    private final RGBColorWriter rw;
    private final PositionWriter pw;

    /**
     * Constructs an instance of this PathWriter
     */
    public PathWriter() {
        glw = new GLineWriter();
        rw = new RGBColorWriter();
        pw = new PositionWriter();
    }

    /**
     * Returns a string representation of the given LineChain instance.
     * If the LineChain is closed, it is considered ad a polygon, and
     * its string representation contains the number of vertexes, the
     * fill color and a list of all the composing segments, together with
     * their stroke color.
     *
     * If the LineChain is not closed, its representation is made up of
     * a simple list of all the composing sections.
     * @param element field element.
     * @return string representation of a LineChain
     */

    @Override
    public String stringOf(P element) {
        if (element.isClosed() && element instanceof LineChain)
            return polygonString(element);
        StringBuilder sb = new StringBuilder();
        element.getPathSections().forEach(s -> sb.append(
                String.format("%s%n",glw.stringOf((GLine) s))));
        return sb.toString();
    }


    private String polygonString(P element) {
        StringBuilder sb = new StringBuilder(
                String.format("POLYGON %d %s%n", element.getPathSections().size(), rw.stringOf(element.fill())));
        element.getPathSections()
                .forEach(s -> sb.append(String.format("%s %s %s%n",
                        pw.stringOf(s.start()),
                        rw.stringOf(s.color().orElse(null)),
                        s.pensize())));
        return sb.toString();
    }
}
