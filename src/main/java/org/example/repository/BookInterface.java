package org.example.repository;

import java.util.List;

public interface BookInterface<T>   {
    List<T> retreiveAll();

    List<T> retreiveSrch();

    void store(T book);


    void removeItemByTag(String bookTagToRemove);
    void removeItemByID(Integer bookIDToRemove);

    boolean searchItem(String findbook);
    void searchItemByID(Integer findbook);

}
