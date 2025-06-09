package com.swapnil.journal.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor

@Document(collection = "config_journal")
public class ConfigJournalAppEntry {


    private String key;
    private String value;


}
