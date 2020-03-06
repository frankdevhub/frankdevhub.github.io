package com.frankdevhub.site.core.generators;

public interface KeyGenerator<T> {
    public abstract T generateKey() throws InterruptedException;
}
