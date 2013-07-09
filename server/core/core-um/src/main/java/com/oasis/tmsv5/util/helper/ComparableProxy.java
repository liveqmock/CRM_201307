package com.oasis.tmsv5.util.helper;

public class ComparableProxy<T> implements Comparable<ComparableProxy<T>> {
    private T target;

    private Object compareValue;

    public ComparableProxy(T target, Object compareValue) {
        this.target = target;
        this.compareValue = compareValue;
    }

    public T getTarget() {
        return this.target;
    }

    public Object getCompareValue() {
        return this.compareValue;
    }

    public int compareTo(ComparableProxy<T> o) {
        if (compareValue == null || o.getCompareValue() == null) {
            return 0;
        }
        if (compareValue.getClass() == Long.class || compareValue.getClass() == Integer.class
                || compareValue.getClass() == Double.class) {
            if (Double.parseDouble(String.valueOf(this.compareValue)) > Double.parseDouble(String.valueOf(o.getCompareValue()))) {
                return 1;
            } else {
                return -1;
            }
        }
        return 0;
    }
}
