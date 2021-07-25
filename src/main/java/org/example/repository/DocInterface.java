package org.example.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface DocInterface<Q>  {

    void saveFile(File file) throws FileNotFoundException;

    List<Q> retreiveAll();

    void findById(Integer docId);
}
