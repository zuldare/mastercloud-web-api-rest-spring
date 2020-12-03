package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.BookCreationDto;
import mastercloud.jh.books.dto.BookDto;
import mastercloud.jh.books.dto.BookReducedDto;
import mastercloud.jh.books.model.Book;
import mastercloud.jh.books.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImplForTesting implements BookService {

    private ConcurrentHashMap<Long, Book> books = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public BookServiceImplForTesting() {
    }

    public BookServiceImplForTesting(Map<Long, Book> books) {
        this.books = (ConcurrentHashMap<Long, Book>) books;
    }


    @Override
    public List<BookReducedDto> getBooks() {
        log.info("Getting all books");
        List<BookReducedDto> booksReduced = books.values().stream()
                .map(book -> BookReducedDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .build())
                .collect(Collectors.toList());
        log.info("Obtained the following books: {}", booksReduced);
        return booksReduced;
    }

    @Override
    public BookDto getBook(Long id) {
        return null;
    }

    @Override
    public Long createBook(BookCreationDto bookCreationDto) {
        return null;
    }
}
