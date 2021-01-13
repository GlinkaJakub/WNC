package com.glinka.wcn.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<Dto, Dao> {

    Dto mapToDto(Dao dao);
    Dao mapToDao(Dto dto);

    default List<Dto> mapToListDto(List<Dao> daoList){
        return daoList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    default List<Dao> mapToListDao(List<Dto> dtoList){
        return dtoList.stream().map(this::mapToDao).collect(Collectors.toList());
    }
}
