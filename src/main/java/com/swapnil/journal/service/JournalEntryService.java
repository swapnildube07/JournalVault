package com.swapnil.journal.service;

import com.swapnil.journal.Entity.JournalEntry;
import com.swapnil.journal.repositary.JournalEntryrepositary;
import com.swapnil.journal.repositary.UserRepository;
import com.swapnil.journal.Entity.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryrepositary journalEntryrepositary;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static  final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryrepositary.save(journalEntry);
            user.getJournalEntry().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            logger.info("SSS");
            throw new RuntimeException(e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryrepositary.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryrepositary.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryrepositary.findById(id);
    }



    @Transactional
    public void deleteById(ObjectId id, String username) {
        try {
            User user = userService.findByUserName(username);
            boolean remove = user.getJournalEntry().removeIf(x -> x.getId().equals(id));
            if (remove) {
                userService.saveUser(user);
                journalEntryrepositary.deleteById(id);
            }
        } catch (Exception e) {
            logger.info("SSSS");
            throw new RuntimeException(e);
        }

    }

    public List<JournalEntry> findByUsername(String username) {
        User user = userService.findByUserName(username);
        if (user != null) {
            return user.getJournalEntry();
        }
        return List.of(); // return empty list if user not found
    }

}
