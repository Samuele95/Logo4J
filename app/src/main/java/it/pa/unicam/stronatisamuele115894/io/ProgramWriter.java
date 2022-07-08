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

package it.pa.unicam.stronatisamuele115894.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This functional interface defines a basic file writer utility.
 * It writes a file associated to a program towards a given destination.
 * The destinations is represented by a PrintWriter instance.
 * The file is passed as a String argument. It may be the program file itself
 * or its output result.
 */

@FunctionalInterface
public interface ProgramWriter {

    PrintWriter writeTo(PrintWriter pw, String string);

    default PrintWriter writeTo(BufferedWriter bw, String string) {
        return writeTo(new PrintWriter(bw,true), string);
    }

    default PrintWriter writeTo(Path programPath, String string) throws IOException {
        return writeTo(Files.newBufferedWriter(programPath), string);
    }

    default PrintWriter writeTo(File programFile, String string) throws IOException {
        return writeTo(programFile.toPath(),string);
    }

    default PrintWriter writeTo(OutputStream os, String string) {
        return writeTo(new BufferedWriter(new OutputStreamWriter(os)),string);
    }
}
