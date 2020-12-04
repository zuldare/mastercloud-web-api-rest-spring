package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.BookDto;
import mastercloud.jh.books.dto.BookReducedDto;
import mastercloud.jh.books.exception.NotFoundException;
import mastercloud.jh.books.model.Book;
import mastercloud.jh.books.service.BookService;
import mastercloud.jh.books.dto.BookCreationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private ConcurrentHashMap<Long, Book> books = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    @Override
    public List<BookReducedDto> getBooks() {
        log.info("Getting all books");
        List<BookReducedDto> booksReduced = collectBooksReduced();
        log.info("Obtained the following books: {}", booksReduced);
        return booksReduced;
    }

    private List<BookReducedDto> collectBooksReduced() {
        return books.values().stream()
                .map(book -> BookReducedDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBook(Long id) {
        log.info("Getting book with id: {}", id);

        if (this.books.containsKey(id)){
            Book bookFound =  this.books.get(id);
            log.info("Book found with was:{}", bookFound);
            return  BookDto.builder()
                    .id(bookFound.getId())
                    .author(bookFound.getAuthor())
                    .publishingHouse(bookFound.getPublishingHouse())
                    .publishYear(bookFound.getPublishYear())
                    .summary(bookFound.getSummary())
                    .title(bookFound.getTitle())
                    .build();
        } else {
            log.info("No user with id: {} have been found", id);
            throw new NotFoundException("Book with id: " + id + " not found");
        }
    }

    @Override
    public Long createBook(BookCreationDto bookCreationDto) {
        return null;
    }
}
