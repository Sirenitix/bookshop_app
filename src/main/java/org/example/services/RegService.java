package org.example.services;

import org.example.entity.Book;
import org.example.entity.RegisForm;
import org.example.repository.NewUserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegService {
    private final NewUserInterface<RegisForm> regRepo;
    @Autowired
    public RegService(NewUserInterface<RegisForm> regRepo) {
        this.regRepo = regRepo;
    }

    public void saveBook(RegisForm regisForm) {
        regRepo.store(regisForm);
    }
    public List<RegisForm> getAllUsers() {
        return regRepo.retreiveAll();
    }

    public void setAut(RegisForm regisForm) {
        regRepo.setAuth(regisForm);
    }


}
