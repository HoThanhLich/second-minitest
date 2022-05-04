package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.BookForm;
import com.codegym.service.book.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("books")
public class BookRestController {

    @Autowired
    private IBookService bookService;

    @Value("${file-upload}")
    private String uploadPath;

    @GetMapping
    public ResponseEntity<Page<Book>> findAllBook(@RequestParam(name = "q") Optional<String> q, @PageableDefault(value = 8) Pageable pageable) {
        Page<Book> books;
        if (!q.isPresent()) {
            books = bookService.findAll(pageable);
        } else {
            books = bookService.findByName(q.get(), pageable);
        }
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> save(@ModelAttribute BookForm bookForm) {
        MultipartFile multipartFile = bookForm.getImage();
        String fileName = bookForm.getImage().getOriginalFilename();
//        long currentTime = System.currentTimeMillis();
//        fileName = fileName + currentTime;
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Book book = new Book(bookForm.getName(), bookForm.getPrice(), bookForm.getAuthor(), fileName,
                bookForm.getCategory());
        bookService.save(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }


    @PostMapping("/{id}")
    public ResponseEntity<Book> editProduct( @PathVariable Long id,@ModelAttribute BookForm bookForm) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MultipartFile multipartFile = bookForm.getImage();
        String image;
        if (multipartFile.getSize() == 0) {
//            image =  bookOptional.get().getImage();
            image = "";
        } else {
            String fileName = multipartFile.getOriginalFilename();
            long currentTime = System.currentTimeMillis();
            fileName = currentTime + fileName;
            image = fileName;
            try {
                FileCopyUtils.copy(bookForm.getImage().getBytes(), new File(uploadPath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Book newBook = new Book(bookOptional.get().getId(), bookForm.getName(), bookForm.getPrice(),bookForm.getAuthor(),
                 image, bookForm.getCategory());
        return new ResponseEntity<>(bookService.save(newBook), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteById(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.remove(id);
        return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Page<Book>> findByCategory(@PathVariable Long id, @PageableDefault(value = 8) Pageable pageable) {
        Page<Book> books = bookService.findByCategory(id, pageable);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
