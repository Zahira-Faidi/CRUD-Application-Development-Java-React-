package ma.Hahn.app.service;

import ma.Hahn.app.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    User save(User user);
    User getUserById(Long id);
}
