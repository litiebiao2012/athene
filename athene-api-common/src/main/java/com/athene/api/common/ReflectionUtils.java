package com.athene.api.common;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by fe on 16/9/18.
 */
public class ReflectionUtils {

    public static List<String> collectionPrefixList;

    static {
        collectionPrefixList = new LinkedList<String>();
        collectionPrefixList.add("java.util.List");
    }


    public static String reflectMethodGenericType(Method method) {
        return "";
    }

    /**
     * int, double, float, long, short, boolean, byte, char， void
     * Integer Double Float Long Short  Boolean Btte   Char Void
     * @param classType
     * @return
     */
    public static boolean isBasicType(String classType) {
        int i = 1;
        char c = 'a';
        boolean b = true;
        float f = 1.0f;
        double d = 2.2;
        long l = 2;

        return false;
    }

    public static boolean isCollectionGenericType(String typeFullName) {
        boolean flag = false;
        for (String prefix : collectionPrefixList) {
            if (typeFullName.contains(prefix)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static String fixTypeName(Type type) {
        String typeName = type.toString();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            typeName = parameterizedType.toString();
        }

        if (typeName.contains("class")) {
            return typeName.split("\\s")[1];
        } else {
            return typeName;
        }
    }

    public static String getCollectionGenericType(String typeFullName) {
        int start = typeFullName.indexOf("<");
        String fullClassName = typeFullName.substring(start + 1,typeFullName.length() - 1);
        return fullClassName;
    }

    public static boolean isCustomized(Class<?> classType) {

        if (classType == boolean.class || classType == byte.class || classType == char.class
                || classType == double.class || classType == float.class || classType == int.class
                || classType == long.class || classType == short.class || classType == Boolean.class
                || classType == Byte.class || classType == Character.class || classType == Double.class
                || classType == Float.class || classType == Integer.class || classType == Long.class
                || classType == Short.class || classType == BigDecimal.class || classType == BigInteger.class
                || classType == String.class || classType == Date.class || classType == AtomicBoolean.class
                || classType == AtomicLong.class || classType == AtomicInteger.class ) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isCustomized(String fullClassName) {

        if (fullClassName.equals(boolean.class.getName()) || fullClassName.equals(byte.class.getName()) || fullClassName.equals(char.class.getName())
                || fullClassName.equals(double.class.getName()) || fullClassName.equals(float.class.getName()) || fullClassName.equals(int.class.getName())
                || fullClassName.equals(long.class.getName()) || fullClassName.equals(short.class.getName()) || fullClassName.equals(Boolean.class.getName())
                || fullClassName.equals(Byte.class.getName()) || fullClassName.equals(Character.class.getName()) || fullClassName.equals(Double.class.getName())
                || fullClassName.equals(Float.class.getName()) || fullClassName.equals(Integer.class.getName()) || fullClassName.equals(Long.class.getName())
                || fullClassName.equals(Short.class.getName()) || fullClassName.equals(BigDecimal.class.getName()) || fullClassName.equals(BigInteger.class.getName())
                || fullClassName.equals(String.class.getName()) || fullClassName.equals(Date.class.getName()) || fullClassName.equals(AtomicBoolean.class.getName())
                || fullClassName.equals(AtomicLong.class.getName()) || fullClassName.equals(AtomicInteger.class.getName())){
            return false;
        } else {
            return true;
        }
    }
}
