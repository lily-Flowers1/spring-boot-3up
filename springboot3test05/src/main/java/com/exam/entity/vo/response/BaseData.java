package com.exam.entity.vo.response;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.function.Consumer;

public interface BaseData {
    default <T> T asViewObject(Class<T> clazz, Consumer<T> consumer){
        T v = this.asViewObject(clazz);
        consumer.accept(v);
        return v;
    }
    default <T> T asViewObject(Class<T> clazz)  {
        try{
            Field[] declaredFields = clazz.getDeclaredFields();
            Constructor<T> constructor = clazz.getConstructor();
            T v = constructor.newInstance();
            for (Field declaredField : declaredFields) convert(declaredField,v);
            return v;
        }catch(ReflectiveOperationException e){
            throw new RuntimeException(e.getMessage());
        }

    }
    private void convert(Field field,Object v) {
        try{
            Field source = this.getClass().getDeclaredField(field.getName());
            field.setAccessible(true);
            source.setAccessible(true);
            field.set(v,source.get(this));
        } catch (IllegalAccessException | NoSuchFieldException ignored) {}

    }
}
