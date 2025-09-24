package contactmanager.service;

import contactmanager.Dto.RegisterRequest;
import contactmanager.model.User;

;

public interface UserService {
    boolean register(RegisterRequest request);
    boolean validateUser(String username, String password);
    User getUserByUsername(String username);
}
