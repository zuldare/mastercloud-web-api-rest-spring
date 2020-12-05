package mastercloud.jh.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mastercloud.jh.books.dto.BookReducedDto;
import mastercloud.jh.books.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class ApiController {

    private final BookService bookService;

    public ApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get a reduced information (id, title) of all books")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Books founds")})
    @GetMapping("/")
    public List<BookReducedDto> getBooks(){
        return this.bookService.getBooks();
    }
}
