package ru.production.ssobolevsky.foodgram.utils;

/**
 * Created by pro on 27.06.2018.
 */

public class CategoriesRepository {

    private static String[] mCategories = new String[] {
            "Супы и бульона",
            "Основные блюда",
            "Завтраки",
            "Салаты",
            "Закуски",
            "Домашняя выпечка",
            "Десерты",
            "Блюда из мяса",
            "Напитки",
            "Пицца"
    };


    public static final String[] getCategories() {
        return mCategories;
    }
}
