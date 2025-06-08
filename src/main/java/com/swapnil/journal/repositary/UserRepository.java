package com.swapnil.journal.repositary;

import com.swapnil.journal.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUsername(String username); // ✅ correct

    void deleteByUsername(String username); // ✅ corrected
}
