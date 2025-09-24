package contactmanager.dao;

import contactmanager.model.User;


public interface UserDao {
    int save(User user);
    User findByUsername(String username);
    int updatePassword(String username, String newPassword);
}
