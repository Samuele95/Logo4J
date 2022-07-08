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

/**
 * This class implements the abstract PolySection class,
 * representing a GeoPath instance whose component sections
 * are instances of GLine class only.
 */
public class LineChain extends PolySection{

    /**
     * Constructor of a LineChain, which takes a
     * List collection of 'GLine' instances as parameters.
     * @param pathSections empty list of GLines
     * @param color stroke color
     * @throws NullPointerException
     *          if the given List is null
     */
    public LineChain(List<GLine> pathSections, RGBColor color){
        super(pathSections, color);
    }

    /**
     * Copy constructor of a LineChain instance.
     * @param obj LineChain instance
     */
    public LineChain(LineChain obj) {
        super(obj);
    }

    /**
     * Returns a deep copy of this LineChain instance.
     * @return deep copy of this LineChain instance.
     */
    @Override
    public GeoPath makeCopy() {
        return new LineChain(this);
    }
}
