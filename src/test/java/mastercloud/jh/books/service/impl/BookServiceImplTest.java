package mastercloud.jh.books.service.impl;

import mastercloud.jh.books.dto.BookCreationDto;
import mastercloud.jh.books.dto.BookDto;
import mastercloud.jh.books.dto.BookReducedDto;
import mastercloud.jh.books.exception.BookNotFoundException;
import mastercloud.jh.books.model.Book;
import mastercloud.jh.books.repository.BookRepository;
import mastercloud.jh.books.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class BookServiceImplTest {
    private static final String UML_SUMMARY = "Learn how to use UML";
    private static final String ADDISON_PUBLISHHOUSE = "Addison";
    private static final String MARTIN_FOWLER_AUTHOR = "Martin Fowler";
    private static final String UML_DISTILLED_TITLE = "UML Distilled";
    private static final String DESIGN_PATTERNS = "Design Patterns";
    private BookService bookService;

    @BeforeEach
    private void setUp(){
        this.bookService = new BookServiceImpl(new BookRepository(), new ModelMapper());
    }

    @Test
     void getBooks_WhenBooksAvailable_ShouldReturnOnlyIdAndTitle(){
        List<BookReducedDto> result = this.bookService.getBooks();
        assertThat(this.bookService.getBooks().size(), is(2));
        assertThat(this.bookService.getBooks().get(0).getId(), is(1L));
        assertThat(this.bookService.getBooks().get(0).getTitle(), is(UML_DISTILLED_TITLE));
        assertThat(this.bookService.getBooks().get(1).getId(), is(2L));
        assertThat(this.bookService.getBooks().get(1).getTitle(), is(DESIGN_PATTERNS));
    }

    @Test
    void getBookById_WhenNotExist_ShouldReturnNotFoundException(){
        assertThrows(BookNotFoundException.class, () -> this.bookService.getBook(5L));
    }

    @Test
    void getBookById_WhenExists_ShouldReturnFullDataOfTheBook(){
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
    void createBook(){
        BookDto bookDto = this.bookService.createBook(BookCreationDto.builder()
                .title("Fake Title")
                .author("Fake Author")
                .publishingHouse("Fake publish house")
                .publishYear(2003)
                .summary("Fake Summary")
                .build());

        assertThat(bookDto.getId(), is(Long.valueOf(3)));
        assertThat(this.bookService.getBooks().size(), is(3));
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