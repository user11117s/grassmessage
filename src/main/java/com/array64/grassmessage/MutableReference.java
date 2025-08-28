package com.array64.grassmessage;

// Why doesn't Java have this?
public class MutableReference<T> {
    private T t;
    public MutableReference() {}
    public MutableReference(T t) {
        this.t = t;
    }
    public void set(T t) {
        this.t = t;
    }
    public T get() {
        return t;
    }
}
