package contactmanager.dao;

import contactmanager.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;
    };


    @Override
    public int save(User user) {
        return jdbcTemplate.update(
                "INSERT INTO users (name, username, password) VALUES (?, ?, ?)",
                user.getName(), user.getUsername(), user.getPassword()
        );
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{username}, userRowMapper);
        return users.isEmpty() ? null : users.get(0);
    }


    @Override
    public int updatePassword(String username, String newPassword) {
        return jdbcTemplate.update(
                "UPDATE users SET password = ? WHERE username = ?",
                newPassword, username
        );
    }
}
