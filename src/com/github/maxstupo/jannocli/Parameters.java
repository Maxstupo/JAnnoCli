package com.github.maxstupo.jannocli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class allows for both easy retrieval of parameter values and {@link #parse(String[], Class[], String[]) parsing} of parameter strings.
 * 
 * @author Maxstupo
 */
public class Parameters {

    private final Object[] objects;
    private final Map<String, Integer> aliasLookup = new HashMap<>();

    private Parameters(Object[] objects, String[] aliases) {
        this.objects = objects;
        for (int i = 0; i < aliases.length; i++)
            aliasLookup.put(aliases[i], i);
    }

    /**
     * Returns the total number of parameters.
     * 
     * @return the total number of parameters.
     */
    public int getCount() {
        return objects.length;
    }

    /**
     * Returns true if the given <code>index</code> is valid.
     * 
     * @param index
     *            the index to check.
     * @return true if the given <code>index</code> is valid.
     */
    public boolean has(int index) {
        return index >= 0 && index < objects.length;
    }

    /**
     * Returns the index for the given alias.
     * 
     * @param alias
     *            the alias to lookup.
     * @return the index for the given alias.
     */
    public int getAliasIndex(String alias) {
        Integer index = aliasLookup.get(alias);
        return (index != null) ? index : -1;
    }

    /**
     * Returns the parameter value at the given index, cast to the given type.
     * 
     * @param index
     *            the index of the parameter.
     * @param type
     *            the type to cast the parameter as.
     * @return the cast parameter or null.
     */
    public <T> T get(int index, Class<T> type) {
        return get(index, type, null);
    }

    /**
     * Returns the parameter value at the given index, cast to the given type. If the parameter doesn't exist the given <code>defaultValue</code> will
     * be returned.
     * 
     * @param index
     *            the index of the parameter.
     * @param type
     *            the type to cast the parameter as.
     * @param defaultValue
     *            the default value to return if the parameter index doesn't exist.
     * @return the cast parameter or the <code>defaultValue</code>.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(int index, Class<T> type, T defaultValue) {
        if (!has(index))
            return defaultValue;
        return (T) objects[index];
    }

    /**
     * Returns the parameter value referenced by the alias given or the default value cast to the given type.
     * 
     * @param alias
     *            the alias of the parameter.
     * @param type
     *            the type to cast the parameter as.
     * @param defaultValue
     *            the default value.
     * @return the parameter value referenced by the alias given or the default value cast to the given type.
     */
    public <T> T get(String alias, Class<T> type, T defaultValue) {
        return get(getAliasIndex(alias), type, defaultValue);
    }

    /**
     * Returns the parameter value at the given index or null.
     * 
     * @param index
     *            the index of the parameter.
     * @return the parameter value at the given index or null.
     */
    public String get(int index) {
        return get(index, (String) null);
    }

    /**
     * Returns the parameter value referenced by the alias given or the default value.
     * 
     * @param alias
     *            the alias of the parameter.
     * @param defaultValue
     *            the default value.
     * @return the parameter value referenced by the alias given or the default value.
     */
    public String get(String alias, String defaultValue) {
        return get(getAliasIndex(alias), defaultValue);
    }

    /**
     * Returns the parameter value referenced by the alias given, or null.
     * 
     * @param alias
     *            the alias of the parameter.
     * @return the parameter value referenced by the alias given, or null.
     */
    public String get(String alias) {
        return get(getAliasIndex(alias));
    }

    /**
     * Returns the parameter value at the given index or the default value.
     * 
     * @param index
     *            the index of the parameter.
     * @param defaultValue
     *            the default value.
     * @return the parameter value at the given index or the default value.
     */
    public String get(int index, String defaultValue) {
        return get(index, String.class, defaultValue);
    }

    /**
     * Returns the parameter value referenced by the alias given, or 0.
     * 
     * @param alias
     *            the alias of the parameter.
     * @return the parameter value referenced by the alias given, or 0.
     */
    public float getFloat(String alias) {
        return getFloat(getAliasIndex(alias));
    }

    /**
     * Returns the parameter value referenced by the alias given or the default value.
     * 
     * @param alias
     *            the alias of the parameter.
     * @param defaultValue
     *            the default value.
     * @return the parameter value referenced by the alias given or the default value.
     */
    public float getFloat(String alias, float defaultValue) {
        return getFloat(getAliasIndex(alias), defaultValue);
    }

    /**
     * Returns the parameter value at the given index or 0.
     * 
     * @param index
     *            the index of the parameter.
     * @return the parameter value at the given index or 0.
     */
    public float getFloat(int index) {
        return getFloat(index, 0F);
    }

    /**
     * Returns the parameter value at the given index or the default value.
     * 
     * @param index
     *            the index of the parameter.
     * @param defaultValue
     *            the default value.
     * @return the parameter value at the given index or the default value.
     */
    public float getFloat(int index, float defaultValue) {
        return get(index, float.class, defaultValue);
    }

    /**
     * Returns the parameter value at the given index or 0.
     * 
     * @param index
     *            the index of the parameter.
     * @return the parameter value at the given index or 0.
     */
    public long getLong(int index) {
        return getLong(index, 0L);
    }

    /**
     * Returns the parameter value referenced by the alias given or the default value.
     * 
     * @param alias
     *            the alias of the parameter.
     * @param defaultValue
     *            the default value.
     * @return the parameter value referenced by the alias given or the default value.
     */
    public long getLong(String alias, long defaultValue) {
        return getLong(getAliasIndex(alias), defaultValue);
    }

    /**
     * Returns the parameter value referenced by the alias given, or 0.
     * 
     * @param alias
     *            the alias of the parameter.
     * @return the parameter value referenced by the alias given, or 0.
     */
    public long getLong(String alias) {
        return getLong(getAliasIndex(alias));
    }

    /**
     * Returns the parameter value at the given index or the default value.
     * 
     * @param index
     *            the index of the parameter.
     * @param defaultValue
     *            the default value.
     * @return the parameter value at the given index or the default value.
     */
    public long getLong(int index, long defaultValue) {
        return get(index, long.class, defaultValue);
    }

    /**
     * Returns the parameter value at the given index or 0.
     * 
     * @param index
     *            the index of the parameter.
     * @return the parameter value at the given index or 0.
     */
    public int getInt(int index) {
        return getInt(index, 0);
    }

    /**
     * Returns the parameter value referenced by the alias given or the default value.
     * 
     * @param alias
     *            the alias of the parameter.
     * @param defaultValue
     *            the default value.
     * @return the parameter value referenced by the alias given or the default value.
     */
    public int getInt(String alias, int defaultValue) {
        return getInt(getAliasIndex(alias), defaultValue);
    }

    /**
     * Returns the parameter value referenced by the alias given, or 0.
     * 
     * @param alias
     *            the alias of the parameter.
     * @return the parameter value referenced by the alias given, or 0.
     */
    public int getInt(String alias) {
        return getInt(getAliasIndex(alias));
    }

    /**
     * Returns the parameter value at the given index or the default value.
     * 
     * @param index
     *            the index of the parameter.
     * @param defaultValue
     *            the default value.
     * @return the parameter value at the given index or the default value.
     */
    public int getInt(int index, int defaultValue) {
        return get(index, int.class, defaultValue);
    }

    /**
     * Returns the parameter value at the given index or false.
     * 
     * @param index
     *            the index of the parameter.
     * @return the parameter value at the given index or false.
     */
    public boolean getBoolean(int index) {
        return getBoolean(index, false);
    }

    /**
     * Returns the parameter value referenced by the alias given, or the default value.
     * 
     * @param alias
     *            the alias of the parameter.
     * @param defaultValue
     *            the default value.
     * @return the parameter value referenced by the alias given, or the default value.
     */
    public boolean getBoolean(String alias, boolean defaultValue) {
        return getBoolean(getAliasIndex(alias), defaultValue);
    }

    /**
     * Returns the parameter value referenced by the alias given, or false.
     * 
     * @param alias
     *            the alias of the parameter.
     * @return the parameter value referenced by the alias given, or false.
     */
    public boolean getBoolean(String alias) {
        return getBoolean(getAliasIndex(alias));
    }

    /**
     * Returns the parameter value at the given index or the default value.
     * 
     * @param index
     *            the index of the parameter.
     * @param defaultValue
     *            the default value.
     * @return the parameter value at the given index or the default value.
     */
    public boolean getBoolean(int index, boolean defaultValue) {
        return get(index, boolean.class, defaultValue);
    }

    /**
     * Parses the given parameter string array and returns a formal parameter object. Use {@link #check(String[], Class[])} to check if parameter
     * string is valid.
     * 
     * @param parameters
     *            the parameters.
     * @param types
     *            the types of each parameter.
     * @param aliases
     *            the aliases of each parameter instead of an index. The {@link Parameters} object uses this to get a parameter value with a name
     *            instead of an index.
     * @return a new {@link Parameters} object containing the parsed parameters given.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Parameters parse(String[] parameters, Class<?>[] types, String[] aliases) {

        Object[] arr = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            String value = parameters[i];
            Class<?> type = i < types.length ? types[i] : null;

            if (type != null) {
                if (type.isEnum()) {
                    arr[i] = Enum.valueOf((Class<Enum>) type, value.toUpperCase());
                } else if (Util.isAssignable(type, float.class)) {
                    arr[i] = Float.parseFloat(value);
                } else if (Util.isAssignable(type, int.class)) {
                    arr[i] = Integer.parseInt(value);
                } else if (Util.isAssignable(type, long.class)) {
                    arr[i] = Long.parseLong(value);
                } else if (Util.isAssignable(type, boolean.class)) {
                    arr[i] = Boolean.parseBoolean(value);
                } else {
                    arr[i] = value;
                }
            } else {
                arr[i] = value;
            }
        }

        return new Parameters(arr, aliases);
    }

    /**
     * Returns true if the given parameter string array matches the corresponding type array.
     * 
     * @param parameters
     *            the parameters in string form.
     * @param types
     *            the type of each parameter. Supported types are {@link Float}, {@link Integer}, {@link Long}, {@link Boolean} and {@link Enum}
     * @return true if the given parameter string array matches the corresponding type array.
     */
    public static boolean check(String[] parameters, Class<?>[] types) {
        if (parameters.length < types.length)
            return false;

        for (int i = 0; i < types.length; i++) {
            Class<?> type = types[i];
            String value = parameters[i];

            if (type.isEnum()) {
                boolean found = false;
                for (Object obj : type.getEnumConstants()) {
                    if (value.toLowerCase().equals(obj.toString().toLowerCase()))
                        found = true;
                }
                if (!found)
                    return false;

            } else if (Util.isAssignable(type, float.class)) {
                if (!Util.isFloat(value))
                    return false;

            } else if (Util.isAssignable(type, int.class)) {
                if (!Util.isInt(value))
                    return false;

            } else if (Util.isAssignable(type, long.class)) {
                if (!Util.isLong(value))
                    return false;

            } else if (Util.isAssignable(type, boolean.class)) {
                if (!value.equalsIgnoreCase("false") && !value.equalsIgnoreCase("true"))
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aliasLookup == null) ? 0 : aliasLookup.hashCode());
        result = prime * result + Arrays.hashCode(objects);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Parameters other = (Parameters) obj;
        if (aliasLookup == null) {
            if (other.aliasLookup != null)
                return false;
        } else if (!aliasLookup.equals(other.aliasLookup))
            return false;
        if (!Arrays.equals(objects, other.objects))
            return false;
        return true;
    }

}
