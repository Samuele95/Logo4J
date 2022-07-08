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

import javafx.scene.paint.Color;
import java.util.List;


/**
 * This record represents a 3-ple of integer RGB values, to represent a color in
 * the sRGB color space. The values in the 3-ple (red,green,blue) represent the
 * value associated to red, green and blue component of the additive RGB model
 * respectively. Two color records having the same RGB values are associated to
 * the same color, and so they are equal according to the general "record" contract.
 * Every instance is immutable.
 *
 * @param red red component of the RGB color model;
 * @param green green component of the RGB color model;
 * @param blue blue component of the RGB color model.
 */

public record RGBColor(int red, int green, int blue) {


    /**
     * Constructs a 3-ple of integer RGB values, to represent a color in
     * the sRGB color space.
     *
     * @param red integer red component of the RGB color model;
     * @param green integer green component of the RGB color model;
     * @param blue integer blue component of the RGB color model.
     *
     * @throws IllegalArgumentException
     *          if red,green or blue parameter is not in the range [0,255]
     */
    public RGBColor {
        if ((red < 0 || red > 255) || (green < 0 || green > 255) || (blue < 0 || blue > 255))
            throw new IllegalArgumentException("RGB values must be in the range [0,255]");
    }

    /**
     * Constructs a 3-ple of integer RGB values, to represent a color in
     * the sRGB color space, from an integer type list. The list must have
     * three elements exactly.
     *
     * @param colorComponents list of three integer values representing
     *                        red, green and blue component
     * @return RGBColor instance
     * @throws IllegalArgumentException
     *          if the argument list doesn't have three elements exactly or
     *          if red,green or blue parameter is not in the range [0,255]
     */

    public static RGBColor create(List<Integer> colorComponents) {
        if (colorComponents.size() != 3)
            throw new IllegalArgumentException("An RGBColor must have exactly three components!");
        return new RGBColor(colorComponents.get(0), colorComponents.get(1), colorComponents.get(2));
    }

    /**
     * Converts this RGBColor instance into JavaFX Color instance
     * @return JavaFX Color instance of this RGBColor element
     */

    public Color toJavaFXColor() {
        return Color.rgb(red,green,blue);
    }


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
    public String toString() {
        return String.format("RGBColor instance:%n" +
                "Red component: %d%n" +
                "Green component: %d%n" +
                "Blue component: %d%n", red, green, blue);
    }
}
