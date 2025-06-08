package com.swapnil.journal.repositary;

import com.swapnil.journal.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryrepositary extends MongoRepository<JournalEntry , ObjectId> {



}
