package ma.Hahn.app.controller;

import lombok.RequiredArgsConstructor;
import ma.Hahn.app.dto.AuthRequest;
import ma.Hahn.app.dto.AuthResponse;
import ma.Hahn.app.entity.User;
import ma.Hahn.app.security.JwtTokenProvider;
import ma.Hahn.app.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImpl userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return userService.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> new AuthResponse(jwtTokenProvider.generateToken(user), user.getEmail()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthResponse(null, "Invalid credentials")));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(java.time.LocalDateTime.now());
        User savedUser = userService.save(user);
        String token = jwtTokenProvider.generateToken(savedUser);
        return new ResponseEntity<>(new AuthResponse(token, savedUser.getEmail()), HttpStatus.CREATED);
    }

}

