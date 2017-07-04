package com.github.maxstupo.jannocli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class provides helper methods used by JAnnoCli.
 * 
 * @author Maxstupo
 */
public final class Util {

    private static final StringBuilder refReplacer = new StringBuilder();
    private static final Map<Class<?>, Class<?>> primitiveToWrapperMap = new HashMap<>();
    private static final Map<Class<?>, Class<?>> wrapperToPrimitiveMap = flipMap(primitiveToWrapperMap);

    private Util() {

    }

    static {
        primitiveToWrapperMap.put(boolean.class, Boolean.class);
        primitiveToWrapperMap.put(int.class, Integer.class);
        primitiveToWrapperMap.put(float.class, Float.class);
        primitiveToWrapperMap.put(double.class, Double.class);
        primitiveToWrapperMap.put(long.class, Long.class);
        primitiveToWrapperMap.put(short.class, Short.class);
        primitiveToWrapperMap.put(byte.class, Byte.class);
    }

    /**
     * Returns true if the given types are assignable.
     * 
     * @param type1
     *            the first type.
     * @param type2
     *            the second type.
     * @return true if the given types are assignable.
     */
    public static boolean isAssignable(Class<?> type1, Class<?> type2) {
        if (type1 == null || type2 == null)
            return false;

        if (type1.isAssignableFrom(type2))
            return true;
        if (type1.isPrimitive()) {
            Class<?> primitive = wrapperToPrimitiveMap.get(type2);
            return type1 == primitive;
        } else {
            Class<?> wrapper = primitiveToWrapperMap.get(type2);
            return wrapper != null && type1.isAssignableFrom(wrapper);
        }
    }

    /**
     * Returns true if the given string can be converted to a integer.
     * 
     * @param n
     *            the string.
     * @return true if the given string can be converted to a integer.
     */
    public static boolean isInt(String n) {
        try {
            Integer.parseInt(n);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    private static <K, V> Map<V, K> flipMap(Map<K, V> map) {
        Map<V, K> flippedMap = new HashMap<>();
        for (Entry<K, V> entry : map.entrySet())
            flippedMap.put(entry.getValue(), entry.getKey());
        return flippedMap;
    }

    /**
     * Returns true if the given string can be converted to a float.
     * 
     * @param n
     *            the string.
     * @return true if the given string can be converted to a float.
     */
    public static boolean isFloat(String n) {
        try {
            Float.parseFloat(n);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Returns true if the given string can be converted to a long.
     * 
     * @param n
     *            the string.
     * @return true if the given string can be converted to a long.
     */
    public static boolean isLong(String n) {
        try {
            Long.parseLong(n);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Replaces '{n}' occurrences with {@link #toString()} of each object within the array.
     * 
     * @param msg
     *            the string to replace references. '{0}, {1}, {2}, etc'
     * @param args
     *            the objects that the references will be replaced with.
     * @return a string.
     */
    public static String replaceReferences(String msg, Object... args) {
        if (args == null)
            return msg;
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null)
                arg = "null";

            refReplacer.delete(0, refReplacer.length());
            refReplacer.append("{").append(i).append("}");

            msg = msg.replace(refReplacer.toString(), arg.toString());
        }
        return msg;
    }

    /**
     * Repeats the given string the specified amount of times.
     * 
     * @param str
     *            the string to repeat.
     * @param times
     *            the number of times to repeat.
     * @return the repeated string.
     */
    public static String strRepeat(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++)
            sb.append(str);
        return sb.toString();
    }

    /**
     * Returns an array shifted to the left.
     * 
     * @param num
     *            the amount to shift left.
     * @param arr
     *            the array to shift.
     * @return the shifted array.
     */
    public static String[] shiftArray(int num, String[] arr) {
        return Arrays.copyOfRange(arr, num, arr.length);
    }

    /**
     * Split a string on each space. Spaces are ignored if within quotes.
     * 
     * @param text
     *            the text to split.
     * @return an array containing parts of the given string.
     */
    public static String[] split(String text) {
        List<String> list = new ArrayList<>();

        StringBuilder buffer = new StringBuilder();

        boolean inQuote = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '"') {
                inQuote = !inQuote;
            } else if (c == ' ' && !inQuote) {
                if (!buffer.toString().isEmpty()) {
                    list.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                }
            } else {
                buffer.append(c);
            }
        }

        if (!buffer.toString().isEmpty()) {
            list.add(buffer.toString());
            buffer.delete(0, buffer.length());
        }

        return list.toArray(new String[list.size()]);
    }
}
