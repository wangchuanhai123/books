package com.example.kzjzdatabase.mapper;


import com.example.kzjzdatabase.entity.Book.Book;
import com.example.kzjzdatabase.entity.Book.Book_NoUrl;
import com.example.kzjzdatabase.entity.GBList;
import com.example.kzjzdatabase.entity.GBList_scope;
import com.example.kzjzdatabase.entity.ReceiveObj.UpdateRecive;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {

    void bookcreate(Book book);

    void bookupdate(UpdateRecive updateRecive);

//    List<Book> getBookAll();

    void bookdelete(String id);

//    Book getBookById(Integer id);

//    List<Book> getBooks(GBList gbList);

    List<Book_NoUrl> getBooks_nourl(GBList gbList);

    List<Book> getBooks(GBList gbList);

    List<Book> getBooks_scope(GBList_scope gbList_scope);

    Book getBooks_byid(String Id);
}
