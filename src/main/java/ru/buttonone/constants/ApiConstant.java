package ru.buttonone.constants;

public final class ApiConstant {
    public static final String BASE_URL = "https://petstore.swagger.io";
    public static final String PET_BY_STATUS = "/v2/pet/findByStatus?status={status}";
    public static final String PET_BY_ID = "/v2/pet/{id}";
    public static final String PET_BY_INVENTORY = "/v2/store/inventory";
    public static final String ADD_NEW_PET = "/v2/pet";
    public static final String ORDER_BY_ID = "/v2/store/order/{id}";
}