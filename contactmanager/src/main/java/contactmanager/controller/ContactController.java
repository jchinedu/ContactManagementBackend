package contactmanager.controller;

import contactmanager.dao.ContactDao;
import contactmanager.model.Contact;
import contactmanager.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactDao contactDao;

    public ContactController(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    private int extractUserIdFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Integer userId = JwtUtil.extractUserId(token);
            if (userId != null) {
                return userId;
            }
        }
        throw new RuntimeException("Invalid or missing Authorization header");
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts(@RequestHeader("Authorization") String authHeader) {
        int userId = extractUserIdFromHeader(authHeader);
        List<Contact> userContacts = contactDao.listByUser(userId);
        return ResponseEntity.ok(userContacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable("id") int id,
                                              @RequestHeader("Authorization") String authHeader) {
        int userId = extractUserIdFromHeader(authHeader);
        Contact contact = contactDao.getByIdAndUser(id, userId);
        if (contact != null) {
            return ResponseEntity.ok(contact);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact,
                                                 @RequestHeader("Authorization") String authHeader) {
        int userId = extractUserIdFromHeader(authHeader);
        contact.setUserId(userId);
        int result = contactDao.save(contact);
        if (result > 0) {
            return ResponseEntity.ok(contact);
        }
        return ResponseEntity.status(500).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContact(@PathVariable("id") int id,
                                                @RequestBody Contact contact,
                                                @RequestHeader("Authorization") String authHeader) {
        int userId = extractUserIdFromHeader(authHeader);
        contact.setId(id);
        contact.setUserId(userId);
        int result = contactDao.update(contact);
        if (result > 0) {
            return ResponseEntity.ok("Contact updated successfully!");
        }
        return ResponseEntity.status(404).body("Contact not found or you do not have permission");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable("id") int id,
                                                @RequestHeader("Authorization") String authHeader) {
        int userId = extractUserIdFromHeader(authHeader);
        int result = contactDao.deleteByIdAndUser(id, userId);
        if (result > 0) {
            return ResponseEntity.ok("Contact deleted successfully!");
        }
        return ResponseEntity.status(404).body("Contact not found or you do not have permission");
    }
}
