package com.oasis.tmsv5.util.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

public class CollectionHelper {

    static public final int MAX_FRAGEMENT_SIZE = 900;

    @SuppressWarnings("hiding")
    public static <T> List<List<T>> seperateList(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("list should not be null");
        }
        List<List<T>> ret = new ArrayList<List<T>>();

        if (list.size() <= MAX_FRAGEMENT_SIZE) {
            ret.add(list);
        } else {
            int fragementCount = list.size() / MAX_FRAGEMENT_SIZE + 1;

            for (int i = 0; i < fragementCount; i++) {
                int start = i * MAX_FRAGEMENT_SIZE;
                int end = start + MAX_FRAGEMENT_SIZE;

                if (end > list.size()) {
                    end = list.size();
                }

                List<T> subList = list.subList(start, end);
                ret.add(subList);
            }

        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    public static boolean checkListType(Object target, Class<?> classz) {
        try {
            if (Collections.checkedCollection((Collection) target, classz).size() > 0) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public static <T> boolean isListIdentical(List<T> listA, List<T> listB) {
        if (listA == null || listB == null) {
            throw new IllegalArgumentException("input list should not be null");
        }

        Object[] arrayA = listA.toArray();
        Object[] arrayB = listB.toArray();

        boolean ret = Arrays.equals(arrayA, arrayB);

        return ret;
    }

    public static <T> List<T> removeDuplicatedItems(List<T> list) {
        Set<T> set = new HashSet<T>();

        set.addAll(list);

        List<T> ret = new ArrayList<T>();
        ret.addAll(set);

        return ret;

    }

    public static <T> List<T> intersectLists(List<List<T>> lists) {
        List<T> ret = new ArrayList<T>();
        if (lists.size() > 0) {
            ret = lists.get(0);
            for (List<T> list : lists) {
                ret = (List<T>) CollectionUtils.intersection((Collection<T>) ret, (Collection<T>) list);
            }
        }

        return ret;
    }
}
