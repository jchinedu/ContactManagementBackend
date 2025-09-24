package contactmanager.controller;

import contactmanager.Dto.AuthResponse;
import contactmanager.Dto.LoginRequest;
import contactmanager.Dto.RegisterRequest;
import contactmanager.model.User;
import contactmanager.service.UserService;
import contactmanager.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        boolean success = userService.register(request);
        if (success) {
            return ResponseEntity.ok("User registered successfully!");
        }
        return ResponseEntity.badRequest().body("Username already exists");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean valid = userService.validateUser(request.getUsername(), request.getPassword());
        if (valid) {
            User user = userService.getUserByUsername(request.getUsername());
            String token = JwtUtil.generateToken(user.getUsername(), user.getId());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
