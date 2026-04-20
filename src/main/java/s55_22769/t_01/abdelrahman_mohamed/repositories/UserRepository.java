package s55_22769.t_01.abdelrahman_mohamed.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import s55_22769.t_01.abdelrahman_mohamed.models.User;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {

    private List<User> users;
    private java.io.File jsonFile;

    public UserRepository() {
        InputStream inputStream = getClass().getResourceAsStream("/users.json");
        if (inputStream == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to read users.json");
        }
        try {
            this.jsonFile = new java.io.File(getClass().getResource("/users.json").toURI());
        } catch (Exception e) {
            this.jsonFile = new java.io.File("/data/users.json");
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            this.users = objectMapper.readValue(inputStream, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading users.json: " + e.getMessage());
        }
    }

    private ObjectMapper mapper() {
        ObjectMapper m = new ObjectMapper();
        m.registerModule(new JavaTimeModule());
        m.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return m;
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    public User save(User user) {
        user.setId(UUID.randomUUID().toString());
        users.add(user);
        try {
            new ObjectMapper().registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .writeValue(jsonFile, users);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving user");
        }
        return user;
    }

    public Optional<User> update(String id, User updated) {
        return findById(id).map(user -> {
            user.setUsername(updated.getUsername());
            user.setEmail(updated.getEmail());
            try {
                new ObjectMapper().registerModule(new JavaTimeModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                        .writeValue(jsonFile, users);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user");
            }
            return user;
        });
    }

    public boolean deleteById(String id) {
        boolean removed = users.removeIf(u -> u.getId().equals(id));
        if (removed) {
            try {
                new ObjectMapper().registerModule(new JavaTimeModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                        .writeValue(jsonFile, users);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting user");
            }
        }
        return removed;
    }
}
