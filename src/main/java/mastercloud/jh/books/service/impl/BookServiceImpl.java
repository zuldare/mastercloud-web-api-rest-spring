package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.books.BookCreationDto;
import mastercloud.jh.books.dto.books.BookDto;
import mastercloud.jh.books.dto.books.BookReducedDto;
import mastercloud.jh.books.dto.books.BookWithCommentsDto;
import mastercloud.jh.books.dto.comments.CommentCreationDto;
import mastercloud.jh.books.dto.comments.CommentDto;
import mastercloud.jh.books.dto.comments.CommentReducedDto;
import mastercloud.jh.books.exception.BookNotFoundException;
import mastercloud.jh.books.exception.CommentNotFoundException;
import mastercloud.jh.books.exception.UserNotFoundException;
import mastercloud.jh.books.model.Book;
import mastercloud.jh.books.model.Comment;
import mastercloud.jh.books.model.User;
import mastercloud.jh.books.repository.BookRepository;
import mastercloud.jh.books.repository.CommentRepository;
import mastercloud.jh.books.repository.UserRepository;
import mastercloud.jh.books.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, CommentRepository commentRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookReducedDto> getBooks() {
        log.info("Getting all books");
        List<Book> books = this.bookRepository.findAll();
        log.info("Obtained the following books: {}", books);
        return books.stream()
                .map(book -> BookReducedDto.builder()
                        .id(book.getId())
                        .title(book.getTitle()).build())
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getBooksComment(Long bookId) {
        log.info("Getting all comments of book with id: {}", bookId);
        Book book = this.bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        return book.getComments()
                .stream()
                .map(comment -> CommentDto
                        .builder()
                        .id(comment.getId())
                        .bookId(book.getId())
                        .commentary(comment.getCommentary())
                        .score(comment.getScore())
                        .userId(comment.getUser().getId())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public BookWithCommentsDto getBook(Long bookId) {
        log.info("Getting book with id: {}", bookId);
        Book book = this.bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        log.info("Book found with was:{}", book);

        return BookWithCommentsDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .summary(book.getSummary())
                .publishingHouse(book.getPublishingHouse())
                .publishYear(book.getPublishYear())
                .comments(new ArrayList<>(this.generateBookComments(book)))
                .build();
    }

    private List<CommentReducedDto> generateBookComments(Book book) {
        return book.getComments()
                .stream()
                .map(comment -> CommentReducedDto
                        .builder()
                        .id(comment.getId())
                        .commentary(comment.getCommentary())
                        .score(comment.getScore())
                        .userEmail(comment.getUser().getEmail())
                        .userNick(comment.getUser().getNick())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public BookDto createBook(BookCreationDto bookCreationDto) {
        log.info("Create new book: {}", bookCreationDto);
        Book newlyCreatedBook = this.bookRepository.save(modelMapper.map(bookCreationDto, Book.class));
        return modelMapper.map(newlyCreatedBook, BookDto.class);
    }


    @Override
    public CommentDto createComment(CommentCreationDto commentCreationDto) {
        log.info("Create new comment: {}", commentCreationDto);
        log.info("Getting book with id: {} ", commentCreationDto.getBookId());

        User user = this.userRepository.findByNick(commentCreationDto.getNick()).orElseThrow(UserNotFoundException::new);
        Book book = this.bookRepository.findById(commentCreationDto.getBookId()).orElseThrow(BookNotFoundException::new);

        Comment newlyCreatedComment = this.commentRepository.save(Comment.builder()
                .commentary(commentCreationDto.getCommentary())
                .book(book)
                .score(commentCreationDto.getScore())
                .user(user)
                .build());

        log.info("Created comment: {}", newlyCreatedComment);
        return modelMapper.map(newlyCreatedComment, CommentDto.class);
    }

    @Override
    public CommentDto deleteComment(Long commentId) {
        log.info("Deleting comment with id: {}", commentId);
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        this.commentRepository.delete(comment);
        return modelMapper.map(comment, CommentDto.class);
    }


}
