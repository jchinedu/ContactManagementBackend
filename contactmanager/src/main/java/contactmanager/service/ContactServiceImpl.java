package contactmanager.service;

import contactmanager.dao.ContactDao;
import contactmanager.model.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactDao dao;

    public ContactServiceImpl(ContactDao dao) {
        this.dao = dao;
    }

    @Override
    public int save(Contact c) {
        return dao.save(c);
    }

    @Override
    public int update(Contact c) {
        return dao.update(c);
    }

    @Override
    public Contact get(int id, int userId) {
        return dao.getByIdAndUser(id, userId);
    }

    @Override
    public int delete(int id, int userId) {
        return dao.deleteByIdAndUser(id, userId);
    }

    @Override
    public List<Contact> list(int userId) {
        return dao.listByUser(userId);
    }
}
