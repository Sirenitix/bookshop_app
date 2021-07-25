package org.example.controllers;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.example.entity.BookTagToFind;
import org.example.entity.BookTagToRemove;
import org.example.entity.Doc;
import org.example.services.BookService;
import org.example.entity.Book;
import org.example.services.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController{

    private final Logger logger = Logger.getLogger(BookShelfController.class);

    private final BookService bookService;

    private final DocService docService;

    @Autowired
    private BookShelfController(DocService docService,BookService bookService){
        this.docService = docService;
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookTagToRemove", new BookTagToRemove());
        model.addAttribute("bookTagToFind", new BookTagToFind());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("docs", docService.getAllDocs());
        model.addAttribute("doc", new Doc());
        return "book_shelf";
    }

    @GetMapping("/searchresult")
    public String searchitem(Model model) {
        logger.info("findbook equal to:");
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookTagToRemove", new BookTagToRemove());
        model.addAttribute("bookTagToFind", new BookTagToFind());
        model.addAttribute("bookList", bookService.getAllsearches());
        model.addAttribute("docs", docService.getAllDocs());
        model.addAttribute("doc", new Doc());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()) {
            logger.info("error");
            model.addAttribute("book", book);
            model.addAttribute("bookTagToRemove", new BookTagToRemove());
            model.addAttribute("bookTagToFind", new BookTagToFind());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("docs",docService.getAllDocs());
            model.addAttribute("doc", new Doc());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookTagToRemove bookTagToRemove, BindingResult bindingResult, Model model)  {
        logger.info("the value of bindingResults: " + bindingResult.hasErrors());
        if(bindingResult.hasErrors()){
            logger.info("error");
            model.addAttribute("book", new Book());
            model.addAttribute("bookTagToRemove", bookTagToRemove);
            model.addAttribute("bookTagToFind", new BookTagToFind());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("docs", docService.getAllDocs());
            model.addAttribute("doc", new Doc());
            return "book_shelf";
        } else {

            if (NumberUtils.isCreatable(String.valueOf(bookTagToRemove.getTag()))) {
                bookTagToRemove.setId(Integer.valueOf(bookTagToRemove.getTag()));
                bookTagToRemove.setSize(Integer.valueOf(bookTagToRemove.getTag()));
                bookService.removeBookByID(bookTagToRemove.getId());
                return "redirect:/books/shelf";
            } else {
                bookTagToRemove.setTitle(bookTagToRemove.getTag());
                bookTagToRemove.setAuthor(bookTagToRemove.getTag());
                logger.info("the value of bookTagToRemove.getId(): " + bookTagToRemove);
                bookService.removeBookByTag(bookTagToRemove.getAuthor());
                bookService.removeBookByTag(bookTagToRemove.getTitle());
                return "redirect:/books/shelf";
            }
        }
    }

    @PostMapping("/clear")
    public String clear(){
         return "redirect:/books/shelf";
    }

    @PostMapping("/search")
    public String findBook(@Valid BookTagToFind bookTagToFind, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            logger.info("error");
            model.addAttribute("book", new Book());
            model.addAttribute("bookTagToRemove", new BookTagToRemove());
            model.addAttribute("bookTagToFind", bookTagToFind);
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("docs", docService.getAllDocs());
            model.addAttribute("doc", new Doc());
            return "book_shelf";
        } else {

            if (bookTagToFind.getFindbook().isEmpty()) {
                return "redirect:/books/shelf";
            }

            if (NumberUtils.isCreatable(bookTagToFind.getFindbook())) {
                bookService.searchItemByID(Integer.valueOf(bookTagToFind.getFindbook()));
                return "redirect:/books/searchresult";
            }

            if (bookService.searchItem(bookTagToFind.getFindbook())) {
                return "redirect:/books/searchresult";
            } else {

                return "redirect:/books/searchresult";
            }

        }
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String conflict(Model model)  {
        model.addAttribute("book", new Book());
        model.addAttribute("bookTagToRemove", new BookTagToRemove());
        model.addAttribute("bookTagToFind", new BookTagToFind());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("docs", docService.getAllDocs());
        model.addAttribute("error", true);
        model.addAttribute("doc", new Doc());
        return "book_shelf";
    }



    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {

        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();
        logger.info("new file saved at:" + serverFile.getAbsolutePath());
        docService.saveFile(serverFile);


        model.addAttribute("book", new Book());
        model.addAttribute("bookTagToRemove", new BookTagToRemove());
        model.addAttribute("bookTagToFind",new BookTagToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("docs", docService.getAllDocs());
        model.addAttribute("doc", new Doc());
        return "redirect:/books/shelf";

    }

    @GetMapping("/downloadFile/{docId}")
    public String download(@PathVariable("docId") Integer docId) {
        docService.downlFile(docId);
        return "redirect:/books/shelf";
    }

}


