package com.oasis.tmsv5.util.helper;

import java.io.Serializable;

public class Range<T> implements Serializable {

    private static final long serialVersionUID = 6706234542137022028L;

    private T begin;

    private T end;

    public T getBegin() {
        return begin;
    }

    public void setBegin(T begin) {
        this.begin = begin;
    }

    public T getEnd() {
        return end;
    }

    public void setEnd(T end) {
        this.end = end;
    }

}
