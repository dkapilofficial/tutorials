package com.baeldung.patterns;

import com.baeldung.patterns.commands.FrontCommand;
import com.baeldung.patterns.commands.UnknownCommand;
import com.baeldung.patterns.data.Bookshelf;
import com.baeldung.patterns.data.BookshelfImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "front-controller", urlPatterns = "/index")
public class FrontControllerServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Bookshelf bookshelf = new BookshelfImpl();
        bookshelf.init();
        getServletContext().setAttribute("bookshelf", bookshelf);
    }

    @Override
    protected void doGet(
      HttpServletRequest request,
      HttpServletResponse response
    ) throws ServletException, IOException {
        doCommand(request, response);
    }

    @Override
    protected void doPost(
      HttpServletRequest request,
      HttpServletResponse response
    ) throws ServletException, IOException {
          doCommand(request, response);
    }

    private void doCommand(
      HttpServletRequest request,
      HttpServletResponse response
    ) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        command.init(request, response);
        command.process();
    }

    private FrontCommand getCommand(HttpServletRequest request) {
        try {
            Class type = Class.forName(
              String.format(
                "com.baeldung.patterns.commands.%sCommand",
                request.getParameter("command")
              )
            );
            return (FrontCommand) type
              .asSubclass(FrontCommand.class)
              .newInstance();
        } catch (Exception e) {
            return new UnknownCommand();
        }
    }
}
