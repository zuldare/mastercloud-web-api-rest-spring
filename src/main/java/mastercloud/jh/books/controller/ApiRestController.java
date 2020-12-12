package mastercloud.jh.books.controller;

import mastercloud.jh.books.dto.*;
import mastercloud.jh.books.service.BookService;
import mastercloud.jh.books.service.CommentService;
import mastercloud.jh.books.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ApiRestController implements ApiRestControllerInterface {

    private final BookService bookService;
    private final CommentService commentService;
    private final UserService userService;

    public ApiRestController(BookService bookService, CommentService commentService, UserService userService) {
        this.bookService = bookService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @Override
    public List<BookReducedDto> getBooks() {
        return this.bookService.getBooks();
    }

    @Override
    public BookDto getBook(Long bookId) {
       return this.bookService.getBook(bookId);
    }

    @Override
    public BookDto createBook(BookCreationDto bookCreationDto) {
        return this.bookService.createBook(bookCreationDto);
    }

    @Override
    public CommentDto deleteComment(Long commentId) {
        return this.commentService.deleteComment(commentId);
    }

    @Override
    public CommentDto createComment(CommentCreationDto commentCreationDto){
        return this.commentService.createComment(commentCreationDto);
    }

    @Override
    public List<UserDto> getUsers() {
        return this.userService.getUsers();
    }

    @Override
    public UserDto getUser(Long userId) {
        return this.userService.getUser(userId);
    }

    @Override
    public UserDto deleteUser(Long userId) {
        return this.userService.deleteUser(userId);
    }

    @Override
    public UserDto createUser(UserCreationModificationDto userCreationModificationDto) {
        return this.userService.createUser(userCreationModificationDto);
    }

    @Override
    public UserDto updateUser(Long userId, @Valid UserCreationModificationDto userCreationModificationDto) {
        return this.userService.updateUser(userId, userCreationModificationDto);
    }
}
