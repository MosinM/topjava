package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;

public interface MealRepository {
    // null if not found, when updated
    Meal save(User user, Meal meal);

    // false if not found
    boolean delete(User user, int id);

    // null if not found
    Meal get(User user, int id);

    Collection<Meal> getAll(User user);
}
