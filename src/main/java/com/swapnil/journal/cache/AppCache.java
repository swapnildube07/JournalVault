    package com.swapnil.journal.cache;
    
    import com.swapnil.journal.Entity.ConfigJournalAppEntry;
    import com.swapnil.journal.repositary.ConfigJournalAppRepository;
    import jakarta.annotation.PostConstruct;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    
    @Component
    public class AppCache {

        @Autowired
        private ConfigJournalAppRepository configJournalAppRepository;



        public Map<String,String> App_Cache = new HashMap<>();
    
        @PostConstruct
        private void init(){
            List<ConfigJournalAppEntry> all = configJournalAppRepository.findAll();
            for(ConfigJournalAppEntry configJournalAppEntry: all){
                App_Cache.put(configJournalAppEntry.getKey(), configJournalAppEntry.getValue());
            }

        }
    

    }
