package com.glinka.wcn;

import com.glinka.wcn.model.dao.Category;
import com.glinka.wcn.controller.dto.CategoryDto;
import com.glinka.wcn.controller.dto.ScientificJournalDto;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.mapper.Mapper;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class GenerateFromCSV {

    private static final String CSV_FILE_PATH = "/home/kuba/IdeaProjects/wcn/src/main/java/com/glinka/wcn/journals.csv";

    private final ScientificJournalService scientificJournalService;
    private final CategoryService categoryService;
    private final Mapper<CategoryDto, Category> categoryMapper;

    public GenerateFromCSV(ScientificJournalService scientificJournalService, CategoryService categoryService, Mapper<CategoryDto, Category> categoryMapper) {
        this.scientificJournalService = scientificJournalService;
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostConstruct
    public void init() throws Exception {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader);
        ) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                ScientificJournalDto sj = new ScientificJournalDto(Long.parseLong(
                        nextRecord[0]),
                        nextRecord[1],
                        nextRecord[2],
                        nextRecord[3],
                        nextRecord[4],
                        nextRecord[5],
                        nextRecord[6],
                        Integer.parseInt(nextRecord[7]),
                        Collections.emptyList());
                List<Category> categories = new ArrayList<>();
                System.out.println("Add journal: " + sj);
                for (int i = 8; i < 52; i++) {
                    if (nextRecord[i].equals("x")){
                        System.out.println("Add category with id: " + i);
                        categories.add(
                                categoryMapper.mapToDao(
                                categoryService.findById((long) (i - 7))
                                ));
                    }
                }
                sj.setCategories(categoryMapper.mapToListDto(categories));
                scientificJournalService.save(sj);
            }
        }
    }
}