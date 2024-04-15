package ru.buttonone;

import ru.buttonone.pojo.Category;
import ru.buttonone.pojo.Pet;
import ru.buttonone.pojo.Tag;
import ru.buttonone.utilities.ConfProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PetStoreTestData {
    private final ConfProperties confProperties = new ConfProperties();

    public Pet petData() {
        return new Pet(Integer.valueOf(confProperties.getProperty("pet-id")),
                categoryData(),
                confProperties.getProperty("pet-name"),
                photoUrlsData(),
                tagData(),
                confProperties.getProperty("pet-status"));
    }

    public Category categoryData() {
        return new Category(Integer.valueOf(confProperties.getProperty("category-id")),
                confProperties.getProperty("category-name"));
    }

    public ArrayList<Tag> tagData() {
        return new ArrayList<>(List.of(new Tag(Integer.valueOf(confProperties.getProperty("tag-id")),
                confProperties.getProperty("tag-name"))));
    }

    public ArrayList<String> photoUrlsData() {
        return new ArrayList<>(Arrays.asList(confProperties.getProperty("pet-photoUrls-list").split(";")));
    }
}