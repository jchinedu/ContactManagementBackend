package contactmanager.service;

import contactmanager.model.Contact;

import java.util.List;

public interface ContactService {
    int save(Contact c);
    int update(Contact contact);
    Contact get(int id, int userId);
    List<Contact> list(int userId);
    int delete(int id, int userId);
}
