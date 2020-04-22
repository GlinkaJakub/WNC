package com.glinka.wcn.service;

import com.glinka.wcn.model.dto.Group;

import java.util.List;

public interface GroupService {

    List<Group> findAll();
    Group findById(int id);

}
