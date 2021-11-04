package com.muntder.tagname.api.registry.base;

public interface Registry<T> {

    @SuppressWarnings("unchecked")
    void register(T... types);

}
