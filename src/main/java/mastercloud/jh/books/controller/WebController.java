package mastercloud.jh.books.controller;

import mastercloud.jh.books.dto.CommentCreationDto;
import mastercloud.jh.books.dto.CommentDto;
import mastercloud.jh.books.service.BookService;
import mastercloud.jh.books.service.CommentService;
import mastercloud.jh.books.service.UserSession;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class WebController {

    private final BookService bookService;
    private final CommentService commentService;
    private final UserSession userSession;
    private final ModelMapper modelMapper;

    public WebController(BookService bookService, CommentService commentService, UserSession userSession, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.commentService = commentService;
        this.userSession = userSession;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/web/books")
    public String getBooks(Model model){
        model.addAttribute("books", this.bookService.getBooks());
        model.addAttribute("userName", userSession.getUser());

        return "initial_template";
    }

    @GetMapping("/web/books/{bookId}")
    public String getBook(Model model, @PathVariable("bookId") Long bookId){
        model.addAttribute("userName", userSession.getUser());
        model.addAttribute("book", this.bookService.getBook(bookId));
        model.addAttribute("bookId", bookId);
        return "particular_book_template";
    }

    @GetMapping("/web/books/form")
    public String openCreateBookWindow(Model model){
        model.addAttribute("userName", userSession.getUser());
        return "newBook_template";
    }

    @PostMapping("/web/books/{bookId}/comments")
    public String createNewComment(Model model, @PathVariable("bookId")Long bookId,
                                   CommentCreationDto commentCreationDto){

        userSession.setUser(commentCreationDto.getAuthor());
        model.addAttribute("userName", commentCreationDto.getAuthor());

        commentCreationDto.setBookId(bookId);
        this.commentService.createComment(commentCreationDto);

        return this.getBook(model, bookId);
    }

    @GetMapping("/web/book/{bookId}/comments/{commentId}")
    public String deleteComment(Model model, @PathVariable("bookId")Long bookId,
                                @PathVariable("commentId") Long commentId){
        model.addAttribute("userName", userSession.getUser());
        this.commentService.deleteComment(commentId);
        return this.getBook(model, bookId);
    }
}
