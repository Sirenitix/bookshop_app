package org.example.services;

import org.example.entity.Book;
import org.example.repository.BookInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Component
public class BookService  {

    private final BookInterface<Book> bookRepo;

    @Autowired
    public BookService(@Qualifier("bookRepository") BookInterface<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public List<Book> getAllsearches(){
        return bookRepo.retreiveSrch();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public void removeBookByTag(String bookTagToRemove) { bookRepo.removeItemByTag(bookTagToRemove); }

    public void removeBookByID(Integer bookIDToRemove) { bookRepo.removeItemByID(bookIDToRemove); }

    public boolean searchItem(String findbook) {return bookRepo.searchItem(findbook);}

    public void searchItemByID(Integer findbook) { bookRepo.searchItemByID(findbook); }


}
