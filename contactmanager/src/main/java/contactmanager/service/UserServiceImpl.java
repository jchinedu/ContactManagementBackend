package contactmanager.service;

import contactmanager.Dto.RegisterRequest;
import contactmanager.dao.UserDao;
import contactmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean register(RegisterRequest request) {
        User existingUser = userDao.findByUsername(request.getUsername());
        if (existingUser != null) {
            return false;
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        userDao.save(newUser);

        return true;
    }


    @Override
    public boolean validateUser(String username, String password) {
        User user = userDao.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
