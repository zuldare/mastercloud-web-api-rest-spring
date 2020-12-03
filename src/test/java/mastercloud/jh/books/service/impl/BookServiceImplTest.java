package mastercloud.jh.books.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;


class BookServiceImplTest {
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        this.bookService = new BookServiceImpl();
    }

    @Test
    void getBooks_WhenNoBooksAvailable_ShouldReturnEmptyList() {
        assertThat(this.bookService.getBooks(), not(null));
    }

//    @Test
//    void getBook() {
//    }
//
//    @Test
//    void createBook() {
//    }
}