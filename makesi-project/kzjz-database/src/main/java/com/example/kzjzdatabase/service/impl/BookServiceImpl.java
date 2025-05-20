package com.example.kzjzdatabase.service.impl;
import com.example.kzjzdatabase.entity.Book.Book;
import com.example.kzjzdatabase.entity.Book.Book_NoUrl;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import com.example.kzjzdatabase.mapper.BookMapper;
import com.example.kzjzdatabase.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;


//    @Override
//    public List<Book> getBookAll() {
//        List<Book> books = bookMapper.getBookAll();
//        return books;
//    }

    @Override
    public void bookcreate(Book book) {
        bookMapper.bookcreate(book);
    }

    @Override
    public void bookupdate(UpdateRecive updateRecive) {
        bookMapper.bookupdate(updateRecive);
    }

    @Override
    public void bookdelete(String Id) {
        bookMapper.bookdelete(Id);
    }

//    @Override
//    public Book getBookById(Integer bookId) {
//        Book book = bookMapper.getBookById(bookId);
//        return book;
//    }

//    @Override
//    public List<Book> getBooks(GBList gbList) {
//        List<Book> books = bookMapper.getBooks(gbList);
//        return books;
//    }

    @Override
    public List<Book_NoUrl> getBooks_nourl(GBList gbList) {
        List<Book_NoUrl> book_noUrlList= bookMapper.getBooks_nourl(gbList);
        return book_noUrlList;
    }

    @Override
    public List<Book> getBooks(GBList gbList) {
        List<Book> bookList= bookMapper.getBooks(gbList);
        return bookList;
    }

    @Override
    public List<Book> getBooks_scope(GBList_scope gbList_scope) {
        List<Book> bookList= bookMapper.getBooks_scope(gbList_scope);
        return bookList;
    }

    @Override
    public Book getBooks_byid(String Id) {
        return bookMapper.getBooks_byid(Id);
    }


}
