package com.glinka.wcn.model.converter;

import java.util.List;

public interface Converter<T, S> {

    T convert(S source);
    T convert(T target, S source);
    List<T> convertToList(List<S> sources);

}
