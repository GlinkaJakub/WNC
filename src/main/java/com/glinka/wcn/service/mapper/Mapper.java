package com.glinka.wcn.service.mapper;

public interface Mapper<Dto, Dao> {

    Dto mapToDto(Dao dao);
    Dao mapToDao(Dto dto);

}
