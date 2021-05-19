package com.nordicmotorhome.Service;

import com.nordicmotorhome.Repository.DatabaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    @Autowired
    DatabaseRepo databaseRepo;

    public void initializeDatabase() {
        databaseRepo.initializeDatabase();
    }
}
