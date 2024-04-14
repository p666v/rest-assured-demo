package ru.buttonone;

public final class EndPoints {
    public static final String PET_STATUS = "/v2/pet/findByStatus?status={status}";
    public static final String ORDER_ID = "/v2/store/order/{id}";
    public static final String PET_ID = "/v2/pet/{id}";
    public static final String PET_INVENTORY = "/v2/store/inventory";
    public static final String PET_ADD = "/v2/pet";
}