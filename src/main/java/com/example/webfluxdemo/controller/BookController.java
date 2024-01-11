package com.example.webfluxdemo.controller;

import com.example.webfluxdemo.Entity.TBook;
import com.example.webfluxdemo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<TBook> save(@RequestBody TBook requestBody){
        return ResponseEntity.ok(bookService.save(requestBody).block());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TBook> findById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TBook>> findAll(){
        return ResponseEntity.ok(bookService.findAll().collectList().block());
    }
}
