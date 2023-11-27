package cams.util;

import cams.camp.Camp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class CampSorter {
    public static void sortCamps(List<Camp> camps, String attributeName, boolean ascending) {
        Comparator<Camp> comparator = (camp1, camp2) -> {
            try {
                Method getter = Camp.class.getMethod("get" + capitalize(attributeName));
                Comparable value1 = (Comparable) getter.invoke(camp1);
                Comparable value2 = (Comparable) getter.invoke(camp2);

                // Handle null values to avoid NullPointerException
                if (value1 == null && value2 == null) return 0;
                if (value1 == null) return ascending ? -1 : 1;
                if (value2 == null) return ascending ? 1 : -1;

                // Custom comparison logic based on type
                if (value1 instanceof LocalDate) {
                    LocalDate date1 = (LocalDate) value1;
                    LocalDate date2 = (LocalDate) value2;
                    return compareValues(date1, date2, ascending);
                } else if (value1 instanceof String) {
                    String str1 = ((String) value1).toUpperCase();
                    String str2 = ((String) value2).toUpperCase();
                    return compareValues(str1, str2, ascending);
                } else if (value1 instanceof Integer) {
                    Integer int1 = (Integer) value1;
                    Integer int2 = (Integer) value2;
                    return compareValues(int1, int2, ascending);
                }

                return ascending ? value1.compareTo(value2) : value2.compareTo(value1);

            } catch (NoSuchMethodException e) {
                System.err.println("Method not found: get" + capitalize(attributeName));
                throw new RuntimeException("Sorting error: Method not found", e);
            } catch (IllegalAccessException e) {
                System.err.println("Cannot access method: get" + capitalize(attributeName));
                throw new RuntimeException("Sorting error: Illegal access", e);
            } catch (InvocationTargetException e) {
                System.err.println("Method threw an exception: get" + capitalize(attributeName));
                throw new RuntimeException("Sorting error: Method threw an exception", e);
            } catch (ClassCastException e) {
                System.err.println("Cannot cast to Comparable for attribute: " + attributeName);
                throw new RuntimeException("Sorting error: Cannot cast to Comparable", e);
            }
        };

        camps.sort(comparator);
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static <T extends Comparable<T>> int compareValues(T value1, T value2, boolean ascending) {
        return ascending ? value1.compareTo(value2) : value2.compareTo(value1);
    }
}
