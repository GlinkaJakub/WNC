package com.glinka.wcn.model.converter.daoToDto;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.User;

public class UserDaoToDtoConverter extends ConverterAdapter<User, UserDao> {

    @Override
    public User convert(User target, UserDao source) {

        if (target == null || source == null)
            return null;

        target.setId(source.getId());
        target.setName(source.getName());
        target.setSurname(source.getSurname());
        target.setEmail(source.getEmail());
        //TODO encrypt password
        target.setPassword(source.getPassword());

        return target;
    }
}
