package com.example.kzjzdatabase.service;

import com.example.kzjzdatabase.entity.Book.Book;
import com.example.kzjzdatabase.entity.Book.Book_NoUrl;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;

import java.util.List;

public interface BookService {

//    List<Book> getBookAll();

    void bookcreate(Book book);

    void bookupdate(UpdateRecive updateRecive);

    void bookdelete(String Id);

//    Book getBookById(Integer bookId);

//    List<Book> getBooks(GBList gbList);

    List<Book_NoUrl> getBooks_nourl(GBList gbList);

    List<Book> getBooks(GBList gbList);

    List<Book> getBooks_scope(GBList_scope gbList_scope);

    Book getBooks_byid(String Id);
}
