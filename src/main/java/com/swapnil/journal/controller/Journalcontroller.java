package com.swapnil.journal.controller;


import com.swapnil.journal.Entity.JournalEntry;
import com.swapnil.journal.repositary.JournalEntryrepositary;
import com.swapnil.journal.service.JournalEntryService;
import com.swapnil.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.swapnil.journal.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class Journalcontroller {


    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> geAllJournaEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> all = user.getJournalEntry();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUserName(username);

        boolean exists = user.getJournalEntry().stream()
                .anyMatch(entry -> entry.getId().equals(id));

        if (!exists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable String id,
                                               @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        if (!ObjectId.isValid(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid journal entry ID format");
        }

        ObjectId objectId = new ObjectId(id);


        User user = userService.findByUserName(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean ownsEntry = user.getJournalEntry().stream()
                .anyMatch(entry -> entry.getId().equals(objectId));

        if (!ownsEntry) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to update this journal entry.");
        }

        JournalEntry oldEntry = journalEntryService.findById(objectId).orElse(null);
        if (oldEntry == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Step 4: Update fields if not empty
        if (newEntry.getTitle() != null && !newEntry.getTitle().trim().isEmpty()) {
            oldEntry.setTitle(newEntry.getTitle());
        }
        if (newEntry.getContent() != null && !newEntry.getContent().trim().isEmpty()) {
            oldEntry.setContent(newEntry.getContent());
        }

        journalEntryService.saveEntry(oldEntry);
        return ResponseEntity.ok(oldEntry);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
      journalEntryService.deleteById(id,username);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}