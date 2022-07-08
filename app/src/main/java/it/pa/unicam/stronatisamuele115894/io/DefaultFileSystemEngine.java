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

import it.pa.unicam.stronatisamuele115894.controller.SessionController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class defines a default implementation of a File System Engine.
 */

public class DefaultFileSystemEngine implements FileSystemEngine {

    ProgramWriter fileSystemWriter;
    ProgramLoader fileSystemLoader;

    public DefaultFileSystemEngine(SessionController controller) {
        fileSystemLoader = new StreamProgramLoader(controller);
        fileSystemWriter = new DefaultProgramWriter(controller);
    }

    @Override
    public boolean loadProgram(InputStream in, OutputStream out) throws IOException {
        Path filePath = findFile(in,out);
        if (Objects.isNull(filePath))
            return false;
        fileSystemLoader.loadProgram(filePath);
        return true;
    }

    @Override
    public Collection<Path> find(String fileName, String searchDirectory) throws IOException {
        try (Stream<Path> files = Files.walk(Paths.get(searchDirectory))) {
            return files
                    .filter(f -> f.getFileName().toString().equals(fileName))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void save(String fileName, String directory, String content) throws IOException {
        if (fileName.indexOf('.') != -1)
            throw new IllegalArgumentException();
        Path path = Paths.get(directory,fileName.concat(".logores"));
        fileSystemWriter.writeTo(path,content);
    }

    @Override
    public ProgramLoader getFileSystemLoader() {
        return fileSystemLoader;
    }

    @Override
    public ProgramWriter getFileSystemWriter() {
        return fileSystemWriter;
    }
}
