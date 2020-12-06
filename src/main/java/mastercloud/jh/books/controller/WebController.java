package mastercloud.jh.books.controller;

import mastercloud.jh.books.service.BookService;
import mastercloud.jh.books.service.CommentService;
import mastercloud.jh.books.service.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final BookService bookService;
    private final CommentService commentService;
    private final UserSession userSession;

    public WebController(BookService bookService, CommentService commentService, UserSession userSession) {
        this.bookService = bookService;
        this.commentService = commentService;
        this.userSession = userSession;
    }

    @GetMapping("/web/books")
    public String getIndex(Model model){
        model.addAttribute("books", this.bookService.getBooks());
        model.addAttribute("userName", userSession.getUser());
        return "initial_template";
    }
}
