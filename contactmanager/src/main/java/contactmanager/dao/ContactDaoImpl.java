package contactmanager.dao;

import contactmanager.model.Contact;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ContactDaoImpl implements ContactDao {

    private final JdbcTemplate jdbcTemplate;

    public ContactDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(Contact c) {
        String sql = "INSERT INTO CONTACT(NAME, EMAIL, ADDRESS, PHONE, USER_ID) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, c.getName(), c.getEmail(), c.getAddress(), c.getPhone(), c.getUserId());
    }

    @Override
    public int update(Contact c) {
        String sql = "UPDATE CONTACT SET NAME=?, EMAIL=?, ADDRESS=?, PHONE=? WHERE CONTACT_ID=? AND USER_ID=?";
        return jdbcTemplate.update(sql, c.getName(), c.getEmail(), c.getAddress(), c.getPhone(),
                c.getId(), c.getUserId());
    }

    @Override
    public Contact getByIdAndUser(int id, int userId) {
        String sql = "SELECT * FROM CONTACT WHERE CONTACT_ID=? AND USER_ID=?";
        return jdbcTemplate.query(sql, new Object[]{id, userId}, (ResultSetExtractor<Contact>) rs -> {
            if (rs.next()) {
                return new Contact(
                        rs.getInt("CONTACT_ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("ADDRESS"),
                        rs.getString("PHONE"),
                        rs.getInt("USER_ID")
                );
            }
            return null;
        });
    }

    @Override
    public int deleteByIdAndUser(int id, int userId) {
        String sql = "DELETE FROM CONTACT WHERE CONTACT_ID=? AND USER_ID=?";
        return jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public List<Contact> listByUser(int userId) {
        String sql = "SELECT * FROM CONTACT WHERE USER_ID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Contact(
                        rs.getInt("CONTACT_ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("ADDRESS"),
                        rs.getString("PHONE"),
                        rs.getInt("USER_ID")
                ), userId);
    }
}
