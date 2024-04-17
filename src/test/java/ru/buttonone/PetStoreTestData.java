package ru.buttonone;

import ru.buttonone.models.Category;
import ru.buttonone.models.Pet;
import ru.buttonone.models.Tag;

import java.util.List;

import static ru.buttonone.constants.CommonConstant.*;

public class PetStoreTestData {

    public Pet petData() {
        return new Pet(PET_ID, categoryData(), PET_NAME, PET_PHOTO_LIST, tagData(), PET_STATUS);
    }

    public Category categoryData() {
        return new Category(CATEGORY_ID, CATEGORY_NAME);
    }

    public List<Tag> tagData() {
        return List.of(new Tag(TAG_ID, TAG_NAME));
    }
}