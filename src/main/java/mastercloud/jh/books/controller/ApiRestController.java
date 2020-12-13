package mastercloud.jh.books.controller;

import mastercloud.jh.books.dto.books.BookCreationDto;
import mastercloud.jh.books.dto.books.BookDto;
import mastercloud.jh.books.dto.books.BookReducedDto;
import mastercloud.jh.books.dto.books.BookWithCommentsDto;
import mastercloud.jh.books.dto.comments.CommentCreationDto;
import mastercloud.jh.books.dto.comments.CommentDto;
import mastercloud.jh.books.dto.users.UserCreationModificationDto;
import mastercloud.jh.books.dto.users.UserDto;
import mastercloud.jh.books.service.BookService;
import mastercloud.jh.books.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ApiRestController implements ApiRestControllerInterface {

    private final BookService bookService;
    private final UserService userService;

    public ApiRestController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @Override
    public List<BookReducedDto> getBooks() {
        return this.bookService.getBooks();
    }

    @Override
    public BookWithCommentsDto getBook(Long bookId) {
        return this.bookService.getBook(bookId);
    }

    @Override
    public BookDto createBook(BookCreationDto bookCreationDto) {
        return this.bookService.createBook(bookCreationDto);
    }

    @Override
    public CommentDto deleteComment(Long commentId) {
        return this.bookService.deleteComment(commentId);
    }

    @Override
    public CommentDto createComment(CommentCreationDto commentCreationDto) {
        return this.bookService.createComment(commentCreationDto);
    }

    @Override
    public List<UserDto> getUsers() {
        return this.userService.getUsers();
    }

    @Override
    public List<CommentDto> getBooksComment(Long bookId) {
        return this.bookService.getBooksComment(bookId);
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
