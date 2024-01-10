package com.zyp.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BeanUtil {
    /**
     * 集合数据的拷贝
     * @param sources: 数据源类
     * @param target: 目标类
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        List<T> list = new ArrayList<>();
        if(CollectionUtils.isEmpty(sources)){
            return list;
        }
        sources.forEach(v->{
            T t = target.get();
            BeanUtils.copyProperties(v, t);
            list.add(t);
        });
        return list;
    }
}
