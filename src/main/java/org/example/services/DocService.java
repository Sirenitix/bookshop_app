package org.example.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.example.entity.Doc;
import org.example.repository.DocInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class DocService  {

    private final DocInterface<Doc> docRepo;

    @Autowired
    public DocService(@Qualifier("docRepository") DocInterface<Doc> docRepo) { this.docRepo = docRepo;}

    public void downlFile(Integer docId){ docRepo.findById(docId);}

    public void saveFile(File file) throws FileNotFoundException {docRepo.saveFile(file);}

    public List<Doc> getAllDocs() { return docRepo.retreiveAll();}


}