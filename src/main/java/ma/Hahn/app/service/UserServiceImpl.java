package ma.Hahn.app.service;

import lombok.RequiredArgsConstructor;
import ma.Hahn.app.entity.User;
import ma.Hahn.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
