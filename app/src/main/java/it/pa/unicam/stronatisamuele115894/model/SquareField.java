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
import java.util.Objects;

/**
 * This class gives a default implementation of the "Field" interface through a
 * 2D square shaped field. Axis range values of the field are [0,width] and [0,height].
 * Elements and shapes put beyond those ranges are not allowed.
 */

public final class SquareField implements Field {

    private final double height;
    private final double width;
    private final List<GeoPath> pathList;
    private GeoPath defaultPath;
    private RGBColor fill;

    private final RGBColor defaultFill;

    public SquareField(double h, double w, RGBColor fill, List<GeoPath> pathList, GeoPath defaultPath) {
        if ((this.height = h) < 0 || (this.width = w) < 0)
            throw new IllegalArgumentException("Axis ranges must be [0, infty[ !");
        setScreenColor(fill);
        defaultFill = fill;
        this.pathList = Objects.requireNonNull(pathList,"Paths need to be drawn on the field!");
        if (!createPath(defaultPath,true))
            throw new IllegalArgumentException();
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public RGBColor getFill() {
        return fill;
    }

    @Override
    public RGBColor getDefaultFill() {
        return defaultFill;
    }

    @Override
    public String setScreenColor(RGBColor fill) {
        RGBColor newfill = Objects.requireNonNull(fill,"A field must have a background color!");
        this.fill = newfill;
        return String.format("Setting stroke color to new RGBColor(%s)", newfill);
    }

    @Override
    public List<GeoPath> getPaths() {
        return pathList;
    }

    @Override
    public GeoPath getDrawingPath() {
        return getPaths().get(getPaths().size()-1);
    }

    @Override
    public GeoPath getDefaultPath() {
        return defaultPath;
    }

    @Override
    public boolean createPath(GeoPath path, boolean setDefault) {
        if (setDefault)
            this.defaultPath = Objects.requireNonNull(path);
        return this.pathList.add(defaultPath.makeCopy());
    }

    @Override
    public boolean draw(GeoSection section, RGBColor stroke, RGBColor fill) throws IllegalAccessException {
        if (!isWithinBounds(Objects.requireNonNull(section).end()))
            return false;
        section.setColor(stroke);
        if ((section.color().isPresent() ^ getDrawingPath().stroke().isPresent()) || getDrawingPath().isClosed()) {
            createPath();
            getDrawingPath().setStroke(section.color().orElse(null));
        }
        getDrawingPath().setFill(fill);
        return getDrawingPath().add(section);
    }

    @Override
    public String clearScreen() {
        pathList.clear();
        setScreenColor(defaultFill);
        createPath();
        return String.format("Field has been cleared!%n");
    }

    @Override
    public boolean isWithinBounds(Position position) {
        double x = Objects.requireNonNull(position, "Position can't be null!").x();
        double y = position.y();
        return (x >= 0 && x <= getWidth()) && (y >= 0 && y <= getHeight());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                String.format("SIZE %.1f %.1f %s\n", this.getWidth(), this.getHeight(), this.getFill()));
        getPaths().stream()
                .filter(p -> p.stroke().isPresent())
                .forEach(s -> sb.append(String.format("%s",s)));
        return sb.toString();
    }
}