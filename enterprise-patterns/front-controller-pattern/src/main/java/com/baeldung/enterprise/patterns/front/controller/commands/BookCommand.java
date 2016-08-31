package com.baeldung.enterprise.patterns.front.controller.commands;

import com.baeldung.enterprise.patterns.front.controller.data.Book;
import com.baeldung.enterprise.patterns.front.controller.data.Bookshelf;

import javax.servlet.ServletException;
import java.io.IOException;

public class BookCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        Book book = Bookshelf.getInstance()
          .findByTitle(request.getParameter("title"));
        if (book != null) {
            request.setAttribute("book", book);
            forward("book-found");
        } else {
            request.setAttribute("books", Bookshelf.getInstance().getBooks());
            forward("book-notfound");
        }
    }
}
