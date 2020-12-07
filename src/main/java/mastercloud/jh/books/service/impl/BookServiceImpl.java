package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.BookCreationDto;
import mastercloud.jh.books.dto.BookDto;
import mastercloud.jh.books.dto.BookReducedDto;
import mastercloud.jh.books.dto.CommentDto;
import mastercloud.jh.books.exception.NotFoundException;
import mastercloud.jh.books.model.Book;
import mastercloud.jh.books.model.Comment;
import mastercloud.jh.books.repository.BookRepository;
import mastercloud.jh.books.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookReducedDto> getBooks() {
        log.info("Getting all books");
        List<BookReducedDto> booksReduced = this.collectBooksReduced();
        log.info("Obtained the following books: {}", booksReduced);
        return booksReduced;
    }

    private List<BookReducedDto> collectBooksReduced() {
        return this.bookRepository.findAll().stream()
                .map(book -> BookReducedDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBook(Long id) {
        log.info("Getting book with id: {}", id);
        Book book = this.getCheckedBook(id);
        log.info("Book found with was:{}", book);
        return modelMapper.map(book, BookDto.class);
    }

    private Book getCheckedBook(Long id) {
        Book book = this.bookRepository.find(id);
        if (isNull(book)) {
            log.info("No user with id: {} have been found", id);
            throw new NotFoundException("Book with id: " + id + " not found");
        }
        return book;
    }

    @Override
    public BookDto createBook(BookCreationDto bookCreationDto) {
        log.info("Create new book: {}", bookCreationDto);
        Book newlyCreatedBook = this.bookRepository.save(modelMapper.map(bookCreationDto, Book.class));
        return modelMapper.map(newlyCreatedBook, BookDto.class);
    }

    @Override
    public void addComment(CommentDto commentDto) {
        log.info("Adding comment: {} to book with id: {}", commentDto, commentDto.getBookId());
        Book book = this.getCheckedBook(commentDto.getBookId());
        if (isNull(book.getComments())){
            book.setComments(new ArrayList<>());
        }
        book.getComments().add(modelMapper.map(commentDto, Comment.class));
    }

    @Override
    public void deleteComment(CommentDto commentDto){
        log.info("Deleting comment: {} from book with id: {}", commentDto, commentDto.getBookId());
        Book book = this.getCheckedBook(commentDto.getBookId());
        book.getComments().remove(modelMapper.map(commentDto, Comment.class));
    }


}
