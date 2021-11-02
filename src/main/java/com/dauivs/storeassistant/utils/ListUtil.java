package com.dauivs.storeassistant.utils;

import java.util.*;

public class ListUtil {

    public static <T> Map<String, T> listToMap(Collection<?> list, Collection<String> keys) {
        if (list == null || keys == null) {
            return new HashMap<>();
        }
        Map<String, T> map = new HashMap<>();
        Iterator<String> iter = keys.iterator();
        for (Object item : list) {
            if (!iter.hasNext()) {
                break;
            }
            map.put(iter.next(), (T) item);
        }
        return map;
    }

    public static <T> Map<String, T> listToMap(Collection<?> list, String...keys) {
        return listToMap(list, Arrays.asList(keys));
    }

    public static List<Map<String, Object>> tableToList(Collection<? extends Collection<?>> table, Collection<String> keys) {
        if (table == null || keys == null) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (Collection<?> row : table) {
            list.add(listToMap(row, keys));
        }
        return list;
    }

    public static List<Map<String, Object>> tableToList(Collection<? extends Collection<?>> table, String...keys) {
        return tableToList(table, Arrays.asList(keys));
    }

    public static List<Map<String, Object>> tableToList(Collection<? extends Collection<?>> table, int headIndex) {
        if (table == null || headIndex < 0) {
            return new ArrayList<>();
        }
        List<String> keys = new ArrayList<>();
        int index = -1;
        List<Map<String, Object>> list = new ArrayList<>();
        for (Collection<?> row : table) {
            ++index;
            if (index < headIndex) {
                continue;
            }
            if (index == headIndex) {
                for (Object value : row) {
                    keys.add(String.valueOf(value));
                }
                continue;
            }
            list.add(listToMap(row, keys));
        }
        return list;
    }

    public static List<Map<String, Object>> tableToList(Collection<? extends Collection<?>> table) {
        return tableToList(table, 0);
    }
}
