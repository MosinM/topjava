package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.UsersUtils.TEST_USER;

public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(1000);

    {
        MealsUtil.MEALS.forEach(meal -> save(TEST_USER, meal));
    }

    @Override
    public Meal save(User user, Meal meal) {
        meal.setUser(user);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(),
                (id, oldMeal) -> {
                    if (oldMeal.getUser().getId().equals(user.getId())) {
                        return meal;
                    } else {
                        return null;
                    }
                });
    }

    @Override
    public boolean delete(User user, int id) {
        if(get(user, id) != null) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(User user, int id) {
        return Optional
                .ofNullable(repository.get(id))
                .filter(m -> m.getUser().getId().equals(user.getId()))
                .orElse(null);
    }

    @Override
    public Collection<Meal> getAll(User user) {
        return repository.values()
                .stream()
                .filter(m -> m.getUser().getId().equals(user.getId()))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

