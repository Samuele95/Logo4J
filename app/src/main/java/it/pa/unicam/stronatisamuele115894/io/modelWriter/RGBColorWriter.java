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

import it.pa.unicam.stronatisamuele115894.model.RGBColor;
import java.util.Objects;

/**
 * This class is used to transform a 'RGBColor' element into a string.
 */
public class RGBColorWriter implements ModelWriter<RGBColor> {


    /**
     * Returns the string representation of this 3-ple of color components.
     * The string consists of the three values in the format "r g b", where
     * "r" is the red component in the RGB color model, "g" is the green
     * component in the RGB color model and "b" is the blue component in the
     * RGB color model.
     *
     * @return string representation of this 3-ple of color components
     */
    @Override
    public String stringOf(RGBColor element) {
        if (Objects.isNull(element))
            return "N N N";
        return String.format("%d %d %d", element.red(), element.green(), element.blue());
    }
}
