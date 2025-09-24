package contactmanager.dao;

import contactmanager.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactDaoTest {

    private DriverManagerDataSource dataSource;
    private ContactDao dao;

    private final int testUserId = 1;

    @BeforeEach
    void setup() {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/contactdb?useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("john");

        dao = new ContactDaoImpl(dataSource);
    }

    @Test
    void testSave() {
        Contact contact = new Contact(0, "john chinedu", "jchinedu080@gmail.com", "semicolon", "09161247818", testUserId);
        int result = dao.save(contact);
        assertTrue(result > 0, "Save operation should affect at least one row");
    }

    @Test
    void testUpdate() {
        Contact contact = new Contact(4, "t-phills", "tphilss@gmail.com", "gombe", "08141994199", 4);
        int result = dao.update(contact);
        assertTrue(result > 0, "Update operation should affect at least one row");
    }

    @Test
    void testGetByIdAndUser() {
        int id = 1;
        Contact contact = dao.getByIdAndUser(id, testUserId);
        assertNotNull(contact, "Contact should not be null for existing id and user");
        System.out.println(contact);
    }

    @Test
    void testDeleteByIdAndUser() {
        int id = 7;
        int result = dao.deleteByIdAndUser(id, testUserId);
        assertTrue(result > 0, "Delete operation should affect at least one row");
    }

    @Test
    void testListByUser() {
        List<Contact> listContacts = dao.listByUser(testUserId);
        assertFalse(listContacts.isEmpty(), "Contact list should not be empty");
        listContacts.forEach(System.out::println);
    }
}
