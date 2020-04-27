package com.glinka.wcn.model.converter.dtoToDao;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.GroupDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupDtoToDaoConverter extends ConverterAdapter<GroupDao, Group> {

    private final UserService userService;
    private final ScientificJournalService scientificJournalService;

    public GroupDtoToDaoConverter(UserService userService, ScientificJournalService scientificJournalService) {
        this.userService = userService;
        this.scientificJournalService = scientificJournalService;
    }

    @Override
    public GroupDao convert(GroupDao target, Group source) {

        if (target == null || source == null)
            return null;

        List<Integer> usersId = new ArrayList<>();
        for(User user : source.getUsers()){
            usersId.add(user.getId());
        }

        List<Integer> journalId = new ArrayList<>();
        for(ScientificJournal journal : source.getJournals()){
            journalId.add(journal.getId());
        }

        target.setId(source.getId());
        target.setName(source.getName());
        target.setUsers(userService.findAllDaoById(usersId));
        target.setJournalDaos(scientificJournalService.findAllDaoById(journalId));

        return target;
    }
}
