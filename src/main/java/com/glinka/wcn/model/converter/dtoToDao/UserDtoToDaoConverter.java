package com.glinka.wcn.model.converter.dtoToDao;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToDaoConverter extends ConverterAdapter<UserDao, User> {

    @Override
    public UserDao convert(UserDao target, User source) {
        if (target == null || source == null)
            return null;

        target.setId(source.getId());
        target.setName(source.getName());
        target.setSurname(source.getSurname());
        target.setEmail(source.getEmail());
        // TODO encrypt password
        target.setPassword(source.getPassword());

        return target;
    }
}
