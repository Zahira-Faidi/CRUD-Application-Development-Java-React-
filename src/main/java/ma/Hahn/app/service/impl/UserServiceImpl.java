package ma.Hahn.app.service.impl;

import lombok.RequiredArgsConstructor;
import ma.Hahn.app.entity.User;
import ma.Hahn.app.repository.UserRepository;
import ma.Hahn.app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}