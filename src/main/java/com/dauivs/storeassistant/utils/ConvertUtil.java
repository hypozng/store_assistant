package com.dauivs.storeassistant.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ConvertUtil {

    public static <T> T to(Object o, Class<T> clazz) {
        if (clazz == null || o == null) {
            return null;
        }
        if (clazz.isAssignableFrom(o.getClass())) {
            return (T) o;
        }
        return null;
    }

    /**
     * 转换为字符串
     *
     * @param o            要转换的实例
     * @param defaultValue 转换失败的默认值
     * @return 转换后的实例
     */
    public static String toStr(Object o, String defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        if (o instanceof String) {
            return (String) o;
        }
        return o.toString();
    }

    /**
     * 转换为字符串
     *
     * @param o 要转换的实例
     * @return 转换后的实例
     */
    public static String toStr(Object o) {
        return toStr(o, "");
    }

    /**
     * 转换为byte
     *
     * @param o            要转换的实例
     * @param defaultValue 转换失败的默认值
     * @return 转换后的实例
     */
    public static byte toByte(Object o, byte defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        if (o instanceof Byte) {
            return (Byte) o;
        }
        if (o instanceof Short) {
            return ((Short) o).byteValue();
        }
        if (o instanceof Integer) {
            return ((Integer) o).byteValue();
        }
        if (o instanceof Long) {
            return ((Long) o).byteValue();
        }
        if (o instanceof Double) {
            return ((Double) o).byteValue();
        }
        if (o instanceof Float) {
            return ((Float) o).byteValue();
        }
        if (o instanceof BigInteger) {
            return ((BigInteger) o).byteValue();
        }
        if (o instanceof BigDecimal) {
            return ((BigDecimal) o).byteValue();
        }
        try {
            return Byte.parseByte(o.toString());
        } catch (NumberFormatException e) {
            try {
                return (byte) Double.parseDouble(o.toString());
            } catch (NumberFormatException e2) {
                return defaultValue;
            }
        }
    }

    /**
     * 转换为byte
     *
     * @param o 要转换的实例
     * @return 转换后的实例
     */
    public static byte toByte(Object o) {
        return toByte(o, (byte) 0);
    }

    /**
     * 转换为short
     *
     * @param o            要转换的实例
     * @param defaultValue 转换失败的默认值
     * @return 转换后的实例
     */
    public static short toShort(Object o, short defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        if (o instanceof Byte) {
            return ((Byte) o).shortValue();
        }
        if (o instanceof Short) {
            return (Short) o;
        }
        if (o instanceof Integer) {
            return ((Integer) o).shortValue();
        }
        if (o instanceof Long) {
            return ((Long) o).shortValue();
        }
        if (o instanceof Double) {
            return ((Double) o).shortValue();
        }
        if (o instanceof Float) {
            return ((Float) o).shortValue();
        }
        if (o instanceof BigInteger) {
            return ((BigInteger) o).shortValue();
        }
        if (o instanceof BigDecimal) {
            return ((BigDecimal) o).shortValue();
        }
        try {
            return Short.parseShort(o.toString());
        } catch (NumberFormatException e) {
            try {
                return (short) Double.parseDouble(o.toString());
            } catch (NumberFormatException e2) {
                return defaultValue;
            }
        }
    }

    /**
     * 转换为short
     *
     * @param o 要转换的实例
     * @return 转换后的实例
     */
    public static short toShort(Object o) {
        return toShort(o, (short) 0);
    }

    /**
     * 转换为int
     *
     * @param o            要转换的实例
     * @param defaultValue 转换失败的默认值
     * @return 转换后的实例
     */
    public static int toInt(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        if (o instanceof Byte) {
            return ((Byte) o).intValue();
        }
        if (o instanceof Short) {
            return ((Short) o).intValue();
        }
        if (o instanceof Integer) {
            return (Integer) o;
        }
        if (o instanceof Long) {
            return ((Long) o).intValue();
        }
        if (o instanceof Double) {
            return ((Double) o).intValue();
        }
        if (o instanceof Float) {
            return ((Float) o).intValue();
        }
        if (o instanceof BigInteger) {
            return ((BigInteger) o).intValue();
        }
        if (o instanceof BigDecimal) {
            return ((BigDecimal) o).intValue();
        }
        String s = o.toString();
        try {
            return Integer.parseInt(o.toString());
        } catch (NumberFormatException e) {
            try {
                return (int) Double.parseDouble(o.toString());
            } catch (NumberFormatException e2) {
                return defaultValue;
            }
        }
    }

    /**
     * 转换为int
     *
     * @param o 要转换的实例
     * @return 转换后的实例
     */
    public static int toInt(Object o) {
        return toInt(o, 0);
    }

    /**
     * 转换为long
     *
     * @param o            要转换的实例
     * @param defaultValue 转换失败的默认值
     * @return 转换后的实例
     */
    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        if (o instanceof Byte) {
            return ((Byte) o).longValue();
        }
        if (o instanceof Short) {
            return ((Short) o).longValue();
        }
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }
        if (o instanceof Long) {
            return (Long) o;
        }
        if (o instanceof Double) {
            return ((Double) o).longValue();
        }
        if (o instanceof Float) {
            return ((Float) o).longValue();
        }
        if (o instanceof BigInteger) {
            return ((BigInteger) o).longValue();
        }
        if (o instanceof BigDecimal) {
            return ((BigDecimal) o).longValue();
        }
        try {
            return Long.parseLong(o.toString());
        } catch (NumberFormatException e) {
            try {
                return (long) Double.parseDouble(o.toString());
            } catch (NumberFormatException e2) {
                return defaultValue;
            }
        }
    }

    /**
     * 转换为long
     *
     * @param o 要转换的实例
     * @return 转换后的实例
     */
    public static long toLong(Object o) {
        return toLong(o, 0);
    }

    /**
     * 转换为double
     *
     * @param o            要转换的实例
     * @param defaultValue 转换失败的默认值
     * @return 转换后的实例
     */
    public static double toDouble(Object o, double defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        if (o instanceof Byte) {
            return ((Byte) o).doubleValue();
        }
        if (o instanceof Short) {
            return ((Short) o).doubleValue();
        }
        if (o instanceof Integer) {
            return ((Integer) o).doubleValue();
        }
        if (o instanceof Long) {
            return ((Long) o).doubleValue();
        }
        if (o instanceof Double) {
            return (Double) o;
        }
        if (o instanceof Float) {
            return ((Float) o).doubleValue();
        }
        if (o instanceof BigInteger) {
            return ((BigInteger) o).doubleValue();
        }
        if (o instanceof BigDecimal) {
            return ((BigDecimal) o).doubleValue();
        }
        try {
            return Double.parseDouble(o.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 转换为double
     *
     * @param o 要转换的实例
     * @return 转换后的实例
     */
    public static double toDouble(Object o) {
        return toDouble(o, 0);
    }

    /**
     * 转换为float
     *
     * @param o            要转换的实例
     * @param defaultValue 转换失败的默认值
     * @return 转换后的实例
     */
    public static float toFloat(Object o, float defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        if (o instanceof Byte) {
            return ((Byte) o).floatValue();
        }
        if (o instanceof Short) {
            return ((Short) o).floatValue();
        }
        if (o instanceof Integer) {
            return ((Integer) o).floatValue();
        }
        if (o instanceof Long) {
            return ((Long) o).floatValue();
        }
        if (o instanceof Double) {
            return ((Double) o).floatValue();
        }
        if (o instanceof Float) {
            return (Float) o;
        }
        if (o instanceof BigInteger) {
            return ((BigInteger) o).floatValue();
        }
        if (o instanceof BigDecimal) {
            return ((BigDecimal) o).floatValue();
        }
        try {
            return Float.parseFloat(o.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 转换为float
     *
     * @param o 要转换的实例
     * @return 转换后的实例
     */
    public static float toFloat(Object o) {
        return toFloat(o, 0);
    }
}
