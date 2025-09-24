package contactmanager.dao;

import contactmanager.model.Contact;

import java.util.List;

public interface ContactDao {
    int save(Contact contact);
    int update(Contact contact);
    Contact getByIdAndUser(int id, int userId);
    int deleteByIdAndUser(int id, int userId);
    List<Contact> listByUser(int userId);
}
