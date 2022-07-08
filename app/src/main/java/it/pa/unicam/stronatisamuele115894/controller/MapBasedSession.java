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


package it.pa.unicam.stronatisamuele115894.controller;

import com.google.inject.Inject;
import it.pa.unicam.stronatisamuele115894.utils.InjTurtle;
import it.pa.unicam.stronatisamuele115894.utils.InjCommands;
import it.pa.unicam.stronatisamuele115894.model.Turtle;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Map-based command list of a Logo Session. This class gives a fixed and immutable
 * implementation of the Session interface. The cursor and the command list dependencies
 * are injected through a separate SessionInjector. Commands are created through a Map
 * data structure which links the string representing the command to the parameters
 * and the action associated to it, where the action is executed by the injected cursor.
 * @param turtle the injected cursor;
 * @param commands the injected string-based commands, created through a Map data structure;

 */

public record MapBasedSession(Turtle turtle, Map<String,BiFunction<Turtle, List<?>,String>> commands) implements Session {

    /**
     * Initializes a map-based session.
     *
     * @param turtle   the injected cursor;
     * @param commands the injected string-based commands, created through a Map data structure;
     */

    @Inject
    public MapBasedSession(@InjTurtle Turtle turtle, @InjCommands Map<String, BiFunction<Turtle, List<?>,String>> commands) {
        this.turtle = Objects.requireNonNull(turtle, "A session needs a cursor");
        this.commands = Objects.requireNonNull(commands, "Commands are needed to direct the cursor");
    }

    /**
     * Returns the set of commands available
     * @return set of commands available
     */

    @Override
    public Set<String> getCommandSet() {
        return commands.keySet();
    }

    /**
     * Returns the given command.
     * @param command the string representing the command
     * @return BiFunction instance of this command, ready to be executed.
     * @throws NullPointerException if the given string is null.
     */
    @Override
    public BiFunction<Turtle, List<?>,String> getCommand(String command) {
        return commands.get(Objects.requireNonNull(command));
    }
}
