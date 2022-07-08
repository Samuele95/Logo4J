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

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import it.pa.unicam.stronatisamuele115894.model.*;
import it.pa.unicam.stronatisamuele115894.utils.InjTurtle;
import it.pa.unicam.stronatisamuele115894.utils.InjField;
import it.pa.unicam.stronatisamuele115894.utils.InjCommands;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * This class injects the necessary parameters to a Session instance.
 */
public class SessionInjector extends AbstractModule {


    /**
     * Injects a Map with the commands available for the user of the session.
     * @return Map with the commands available for the user of the session.
     */
    @Provides
    @InjCommands
    public static Map<String, BiFunction<Turtle, List<?>,String>> provideCommands() {
        Map<String, BiFunction<Turtle, List<?>,String>> commandmap = new HashMap<>();
        addIntegerSingleArgumentFunctions(commandmap);
        addIntegerListArgumentFunctions(commandmap);
        addNoArgumentFunctions(commandmap);
        addRepeatCommand(commandmap);
        return commandmap;
    }

    /**
     * Injects the default cursor parameters of this session.
     * @return default cursor parameters of this session.
     */
    @Provides
    @InjTurtle
    public static Turtle provideTurtle() {
        Field field = SessionInjector.provideField();
        return new DefaultTurtle(Objects.requireNonNull(field),
                new Position(field.getWidth()/2, field.getHeight()/2),
                new RGBColor(0, 0, 0),
                new RGBColor(255, 255, 255),
                1);
    }

    /**
     * Injects the default canvas used to draw during this session.
     * @return default canvas used to draw during this session.
     */
    @Provides
    @InjField
    public static Field provideField() {
        return new SquareField(
                200f,
                200f,
                new RGBColor(255,255,255),
                new ArrayList<>(),
                new LineChain(new ArrayList<>(), new RGBColor(0,0,0)));
    }


    /**
     * Adds the RIPETI command to the session
     */
    private static void addRepeatCommand(Map<String,BiFunction<Turtle, List<?>,String>> commands) {
        commands.put("RIPETI" , (t,obj) -> createRepeatCommand(t,obj,commands));
    }

    /**
     * Adds some defined commands taking a list of Integer as argument.
     */
    private static void addIntegerListArgumentFunctions(Map<String,BiFunction<Turtle, List<?>,String>> commands) {
        commands.put("SETPENCOLOR", (t,obj) -> t.setStroke(RGBColor.create(convertToInt(obj,3))));
        commands.put("SETFILLCOLOR", (t,obj) -> t.setFill(RGBColor.create(convertToInt(obj,3))));
        commands.put("SETSCREENCOLOR", (t,obj) -> t.getField().setScreenColor(RGBColor.create(convertToInt(obj,3))));
    }

    /**
     * Adds some defined commands taking an Integer argument.
     */
    private static void addIntegerSingleArgumentFunctions(Map<String,BiFunction<Turtle, List<?>,String>> commands) {
        commands.put("FORWARD", (t,obj) -> forward(t, convertToInt(obj,1,0)));
        commands.put("BACK", (t,obj) -> back(t, convertToInt(obj,1,0)));
        commands.put("RIGHT",(t,obj) -> right(t,convertToInt(obj,1,0)));
        commands.put("LEFT",(t,obj) -> left(t,convertToInt(obj,1,0)));
        commands.put("SETPENSIZE",(t,obj) -> t.setPenSize(convertToInt(obj,1,0)));
    }

    /**
     * Adds some defined commands taking no argument.
     */
    private static void addNoArgumentFunctions(Map<String,BiFunction<Turtle, List<?>,String>> commands) {
        commands.put("CLEARSCREEN", (t,obj) -> t.clearScreen());
        commands.put("HOME", (t,obj) -> home(t));
        commands.put("PENUP", (t,obj) -> t.setPlot(false));
        commands.put("PENDOWN", (t,obj) -> t.setPlot(true));
    }


    /**
     * Converts the first "n" elements of a given List to the given
     * Class type, if they can be converted. No conversion is done
     * otherwise.
     */
    private static <T> List<T> convertTo(List<?> obj, int n, Class<T> type) {
        if (Objects.requireNonNull(obj).size() != n)
            throw new IllegalArgumentException();
        return obj.stream()
                .filter(Objects::nonNull)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    /**
     * Converts the first "n" elements of a given List to Integer, if
     * they can be converted. No conversion is done otherwise.
     */
    private static List<Integer> convertToInt(List<?> obj, int n) {
        if (Objects.requireNonNull(obj).size() != n)
            throw new IllegalArgumentException();
        return obj.stream()
                .filter(Objects::nonNull)
                .filter(s -> s instanceof String)
                .map(s -> Integer.parseInt((String) s))
                .collect(Collectors.toList());
    }

    /**
     * Takes the object at index "index" from the given list, and converts
     * it to the given Class type, if possible.No conversion is done otherwise.
     */
    private static <T> T convertTo(List<Object> obj, int n, Class<T> type, int index) {
        return convertTo(obj,n,type).get(index);
    }

    /**
     * Takes the object at index "index" from the given list, and converts
     * it to Integer type, if possible. No conversion is done otherwise.
     */
    private static Integer convertToInt(List<?> list, int n, int index) {
        return convertToInt(list,n).get(index);
    }

    /**
     * Creates a "FORWARD" command for the cursor.
     */
    private static String forward(Turtle t, int n) {
        return t.move(n,-n, new GLine(t.getPosition(), t.computeNewPosition(n,-n),t.getPenSize()));
    }

    /**
     * Creates a "BACK" command for the cursor.
     */
    private static String back(Turtle t, int n) {
        return t.move(-n,n, new GLine(t.getPosition(), t.computeNewPosition(-n,n),t.getPenSize()));
    }

    /**
     * Creates a "RIGHT" command for the cursor.
     */
    private static String right(Turtle t, int degrees) {
        return t.setDirection(t.getDirection()-degrees);
    }

    /**
     * Creates a "LEFT" command for the cursor.
     */
    private static String left(Turtle t, int degrees) {
        return t.setDirection(t.getDirection()+degrees);
    }

    /**
     * Creates a "HOME" command for the cursor.
     */
    private static String home(Turtle t) {
        if (t.getPlot())
            t.setPlot(false);
        Position homePosition = new Position(t.getField().getWidth() / 2, t.getField().getHeight() / 2);
        String result = t.move( homePosition, new GLine(t.getPosition(), homePosition,t.getPenSize()));
        t.setPlot(true);
        return result;
    }

    /**
     * Creates a "RIPETI" command for the cursor. The RIPETI command must have this syntax.
     *
     * This method runs a list of BiFunction instances representing the commands.The list is
     * created by spitting into autonomous lists (through the private method convertCommandsToStringList)
     * the string between brackets representing the commands to be repeated. Every list is made from
     * the command string and its arguments. Every list is then added to a higher-order list. The process
     * is repeated a number of times equal to the first integer argument of the RIPETI command.
     */
    private static String createRepeatCommand(Turtle t, List<?> obj, Map<String,BiFunction<Turtle, List<?>,String>> commands) {
        if (obj.size() != 2)
            throw new IllegalArgumentException("'RIPETI' command must have two arguments!");
        int n = Integer.parseInt((String) obj.get(0));
        String args = ((String) obj.get(1)).replaceAll("\\p{P}", "");
        List<String> parsedArguments = Arrays.stream(args.split("\\s+(?![^\\[]*\\])")).toList();
        List<List<String>> listOfRepeatedCommands = convertCommandsToStringList(parsedArguments, commands, n);
        for (List<String> command : listOfRepeatedCommands) {
            commands.get(command.get(0)).apply(t, command.subList(1, command.size()));
        }
        return String.format("Executed sequence of commands!%n");
    }

    /**
     * Converts a string of given commands to a list of the commands themselves and their own arguments.
     * 'n' instances of this list are then added to an higher-order list returned by this
     * method. The 'n' instances are necessary in order to implement a proper "RIPETI" command.
     * During the conversion, it checks whether the command is available in this session,
     */
    private static List<List<String>> convertCommandsToStringList(List<String> obj, Map<String,BiFunction<Turtle, List<?>,String>> commands, int n) {
        List<List<String>> commandSequence = new ArrayList<>();
        try {
            for (int i = 0; i < n; i++) {
                for (String s : obj) {
                    if (commands.containsKey(s))
                        commandSequence.add(new ArrayList<>());
                    commandSequence.get(commandSequence.size() - 1).add(s);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Syntax error in 'RIPETI' command!");
        }
        return commandSequence;
    }
}
