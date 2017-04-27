package com.github.maxstupo.jannocli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maxstupo
 *
 */
public class Parameters {

    private final Object[] objects;
    private final Map<String, Integer> aliasLookup = new HashMap<>();

    private Parameters(Object[] objects, String[] aliases) {
        this.objects = objects;
        for (int i = 0; i < aliases.length; i++)
            aliasLookup.put(aliases[i], i);
    }

    public int getCount() {
        return objects.length;
    }

    public boolean has(int index) {
        return index >= 0 && index < objects.length;
    }

    public int getAliasIndex(String alias) {
        return aliasLookup.get(alias);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int index, Class<T> type, T defaultValue) {
        if (!has(index))
            return defaultValue;
        return (T) objects[index];
    }

    public <T> T get(String alias, Class<T> type, T defaultValue) {
        return get(getAliasIndex(alias), type, defaultValue);
    }

    public String get(int index) {
        return get(index, null);
    }

    public String get(String alias, String defaultValue) {
        return get(getAliasIndex(alias), defaultValue);
    }

    public String get(String alias) {
        return get(getAliasIndex(alias));
    }

    public String get(int index, String defaultValue) {
        return get(index, String.class, defaultValue);
    }

    public float getFloat(String alias) {
        return getFloat(getAliasIndex(alias));
    }

    public float getFloat(String alias, float defaultValue) {
        return getFloat(getAliasIndex(alias), defaultValue);
    }

    public float getFloat(int index) {
        return getFloat(index, 0F);
    }

    public float getFloat(int index, float defaultValue) {
        return get(index, float.class, defaultValue);
    }

    public long getLong(int index) {
        return getLong(index, 0L);
    }

    public long getLong(String alias, long defaultValue) {
        return getLong(getAliasIndex(alias), defaultValue);
    }

    public long getLong(String alias) {
        return getLong(getAliasIndex(alias));
    }

    public long getLong(int index, long defaultValue) {
        return get(index, long.class, defaultValue);
    }

    public int getInt(int index) {
        return getInt(index, 0);
    }

    public int getInt(String alias, int defaultValue) {
        return getInt(getAliasIndex(alias), defaultValue);
    }

    public int getInt(String alias) {
        return getInt(getAliasIndex(alias));
    }

    public int getInt(int index, int defaultValue) {
        return get(index, int.class, defaultValue);
    }

    public boolean getBoolean(int index) {
        return getBoolean(index, false);
    }

    public boolean getBoolean(String alias, boolean defaultValue) {
        return getBoolean(getAliasIndex(alias), defaultValue);
    }

    public boolean getBoolean(String alias) {
        return getBoolean(getAliasIndex(alias));
    }

    public boolean getBoolean(int index, boolean defaultValue) {
        return get(index, boolean.class, defaultValue);
    }

    public static Parameters parse(String[] parameters, Class<?>[] types, String[] aliases) {

        Object[] arr = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            String value = parameters[i];
            Class<?> type = i < types.length ? types[i] : null;

            if (type != null) {
                if (Util.isAssignable(type, float.class)) {
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

    public static boolean check(String[] parameters, Class<?>[] types) {
        if (parameters.length < types.length)
            return false;

        for (int i = 0; i < types.length; i++) {
            Class<?> type = types[i];
            String value = parameters[i];

            if (Util.isAssignable(type, float.class)) {
                if (!Util.isFloat(value))
                    return false;
            } else if (Util.isAssignable(type, int.class)) {
                if (!Util.isInt(value))
                    return false;
            } else if (Util.isAssignable(type, long.class)) {
                if (!Util.isLong(value))
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
