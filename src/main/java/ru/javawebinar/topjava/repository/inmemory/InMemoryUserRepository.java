package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.UsersUtils.ADMIN;
import static ru.javawebinar.topjava.util.UsersUtils.TEST_USER;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private static AtomicInteger counter = new AtomicInteger(1000);
    private static Map<Integer, User> repository = new ConcurrentHashMap<>();

    static {
        repository.put(1, ADMIN);
        repository.put(2, TEST_USER);
    }
    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        if(repository.containsKey(id)) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(user.getId(), (id, oldMeal) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values()
                .stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findAny()
                .orElse(null);
    }
}
