package com.glinka.wcn.model.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ConverterAdapter<T, S> implements Converter<T, S> {

    @Override
    public T convert(S source) {
        return convert(getInstance(), source);
    }

    @Override
    public List<T> convertToList(List<S> sources) {
        List<T> result = new ArrayList<>();
        if (sources != null && !sources.isEmpty())
            sources.forEach(s -> result.add(convert(s)));

        return result.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private T getInstance() {
        String targetClassName = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
        try{
            Constructor<T> cons = (Constructor<T>) Class.forName(targetClassName).getDeclaredConstructor();
            return cons.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
