package mastercloud.jh.books.service.impl;

import mastercloud.jh.books.dto.BookCreationDto;
import mastercloud.jh.books.dto.BookDto;
import mastercloud.jh.books.exception.NotFoundException;
import mastercloud.jh.books.model.Book;
import mastercloud.jh.books.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class BookServiceImplTest {
    private static final String UML_SUMMARY = "Learn to use UML";
    private static final String ADDISON_PUBLISHHOUSE = "Addison";
    private static final String MARTIN_FOWLER_AUTHOR = "Martin Fowler";
    private static final String UML_DISTILLED_TITLE = "UML Distilled";
    private BookService bookService;

    @BeforeEach
    void setUp() {
        this.bookService = new BookServiceImpl();
    }

    @Test
    public void getBooks_WhenNoBooksAvailable_ShouldReturnEmptyList() {
        assertThat(this.bookService.getBooks().size(), is(0));
    }

    @Test
    public void getBooks_WhenBooksAvailable_ShouldReturnOnlyIdAndTitle(){
        this.bookService = this.bookServiceWithInitializedValues();
        assertThat(this.bookService.getBooks().size(), is(1));
        assertThat(this.bookService.getBooks().get(0).getId(), is(1L));
        assertThat(this.bookService.getBooks().get(0).getTitle(), is(UML_DISTILLED_TITLE));
    }

    @Test
    public void getBookById_WhenNotExist_ShouldReturnNotFoundException(){
        assertThrows(NotFoundException.class, () -> this.bookService.getBook(1L));
    }

    @Test
    public void getBookById_WhenExists_ShouldReturnFullDataOfTheBook(){
        this.bookService = this.bookServiceWithInitializedValues();
        BookDto bookDto = this.bookService.getBook(1L);
        assertNotNull(bookDto);
        Book testingBook = this.bookBuilder();
        assertThat(bookDto.getId(), is(testingBook.getId()));
        assertThat(bookDto.getAuthor(), is(testingBook.getAuthor()));
        assertThat(bookDto.getPublishYear(), is(testingBook.getPublishYear()));
        assertThat(bookDto.getPublishingHouse(), is(testingBook.getPublishingHouse()));
        assertThat(bookDto.getSummary(), is(testingBook.getSummary()));
        assertThat(bookDto.getTitle(), is(testingBook.getTitle()));
    }

    @Test
    public void createBook(){
        this.bookService = this.bookServiceWithInitializedValues();

        assertThat(this.bookService.getBooks().size(), is(1));

        for (int i=1; i<=2; i++){
            Long createdBookId = this.bookService.createBook(BookCreationDto.builder()
                    .title(UML_DISTILLED_TITLE)
                    .author(MARTIN_FOWLER_AUTHOR)
                    .publishingHouse(ADDISON_PUBLISHHOUSE)
                    .publishYear(2003)
                    .summary(UML_SUMMARY)
                    .build());

            assertThat(createdBookId, is(Long.valueOf(i)));
            assertThat(this.bookService.getBooks().size(), is(i));
        }


    }

    private BookService bookServiceWithInitializedValues() {
        ConcurrentHashMap<Long, Book> map = new ConcurrentHashMap<>();
        map.put(1L, this.bookBuilder());
        return new BookServiceImplForTesting(map);
    }

    private Book bookBuilder(){
        return Book.builder()
                .id(1L)
                .title(UML_DISTILLED_TITLE)
                .author(MARTIN_FOWLER_AUTHOR)
                .publishingHouse(ADDISON_PUBLISHHOUSE)
                .publishYear(2003)
                .summary(UML_SUMMARY)
                .build();
    }
}