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

import it.pa.unicam.stronatisamuele115894.model.Field;
import it.pa.unicam.stronatisamuele115894.model.GeoPath;

/**
 * This class is used to transform a 'Field' element into a string.
 */

public class FieldWriter implements ModelWriter<Field> {

    private final PathWriter<GeoPath> lw;
    private final RGBColorWriter rw;

    /**
     * Constructs an instance of this PathWriter
     */

    public FieldWriter() {
        lw = new PathWriter<GeoPath>();
        rw = new RGBColorWriter();
    }

    /**
     * Returns a string representation of this field instance.
     * The string is composed by the size of the field, together
     * with a sequence of string representations of all the paths
     * which compose this field.
     * @param element field element.
     * @return string representation of Field.
     */
    @Override
    public String stringOf(Field element) {
        StringBuilder sb = new StringBuilder(
                String.format("SIZE %.1f %.1f %s\n", element.getWidth(), element.getHeight(), rw.stringOf(element.getFill())));
        element.getPaths().stream()
                .filter(p -> p.stroke().isPresent())
                .forEach(p -> sb.append(lw.stringOf(p)));
        return sb.toString();
    }
}
