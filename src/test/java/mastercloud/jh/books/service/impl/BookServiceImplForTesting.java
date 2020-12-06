package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.BookCreationDto;
import mastercloud.jh.books.dto.BookDto;
import mastercloud.jh.books.dto.BookReducedDto;
import mastercloud.jh.books.exception.NotFoundException;
import mastercloud.jh.books.model.Book;
import mastercloud.jh.books.service.BookService;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;


    public BookServiceImplForTesting(Map<Long, Book> books) {
        this.books = (ConcurrentHashMap<Long, Book>) books;
        this.modelMapper = new ModelMapper();
    }


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
            return  modelMapper.map(bookFound, BookDto.class);
        } else {
            log.info("No user with id: {} have been found", id);
            throw new NotFoundException("Book with id: " + id + " not found");
        }
    }

    @Override
    public BookDto createBook(BookCreationDto bookCreationDto) {
        log.info("Create new book: {}", bookCreationDto);
        Long next = nextId.incrementAndGet();

        this.books.computeIfAbsent(next, key -> Book.builder()
                .title(bookCreationDto.getTitle())
                .summary(bookCreationDto.getSummary())
                .publishYear(bookCreationDto.getPublishYear())
                .publishingHouse(bookCreationDto.getPublishingHouse())
                .author(bookCreationDto.getAuthor())
                .id(key)
                .build());
        return modelMapper.map(books.get(next), BookDto.class);
    }
}
