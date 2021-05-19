package com.dauivs.storeassistant.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str) {
        return isEmpty(ConvertUtil.toStr(str));
    }

    /**
     * 将下划线命名风格转换为驼峰命名风格
     *
     * @param source
     * @return
     */
    public static String lineToHump(CharSequence source) {
        if (isEmpty(source)) {
            return "";
        }
        StringBuilder target = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < source.length(); ++i) {
            char ch = source.charAt(i);
            if (ch == '_') {
                if (target.length() > 0) {
                    flag = true;
                }
                continue;
            }
            if (flag) {
                target.append(Character.toUpperCase(ch));
                flag = false;
                continue;
            }
            target.append(Character.toLowerCase(ch));
        }
        return target.toString();
    }

    /**
     * 将下划线命名风格转换为驼峰命名风格
     *
     * @param map
     * @return
     */
    public static <V> Map<String, V> lineToHump(Map<String, V> map) {
        if (map == null) {
            return null;
        }
        Map<String, V> result = new HashMap<>();
        map.forEach((key, value) -> result.put(lineToHump(key), value));
        return result;
    }

    /**
     * 将下划线命名风格转换为驼峰命名风格
     *
     * @param list
     * @return
     */
    public static <V> List<Map<String, V>> lineToHump(List<? extends Map<String, V>> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(StringUtil::lineToHump).collect(Collectors.toList());
    }

    /**
     * 将驼峰命名风格转换为下划线命名风格
     *
     * @param source
     * @return
     */
    public static String humpToLine(CharSequence source) {
        if (isEmpty(source)) {
            return "";
        }
        StringBuilder target = new StringBuilder();
        for (int i = 0; i < source.length(); ++i) {
            char ch = source.charAt(i);
            if (i > 0 && Character.isUpperCase(ch)) {
                if (Character.isLowerCase(source.charAt(i - 1)) || (i < source.length() - 1 && Character.isLowerCase(source.charAt(i + 1)))) {
                    target.append('_');
                }
            }
            target.append(Character.toLowerCase(ch));
        }
        return target.toString();
    }

    /**
     * 将驼峰命名风格转换为下划线命名风格
     *
     * @param map
     * @return
     */
    public static <V> Map<String, V> humpToLine(Map<String, V> map) {
        if (map == null) {
            return null;
        }
        HashMap<String, V> result = new HashMap<>();
        map.forEach((key, value) -> result.put(humpToLine(key), value));
        return result;
    }

    /**
     * 将驼峰命名风格转换为下划线命名风格
     *
     * @param list
     * @return
     */
    public static <V> List<Map<String, V>> humpToLine(List<? extends Map<String, V>> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(StringUtil::humpToLine).collect(Collectors.toList());
    }
}
