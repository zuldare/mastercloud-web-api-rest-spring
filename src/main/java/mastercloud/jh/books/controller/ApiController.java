package mastercloud.jh.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mastercloud.jh.books.dto.BookCreationDto;
import mastercloud.jh.books.dto.BookDto;
import mastercloud.jh.books.dto.BookReducedDto;
import mastercloud.jh.books.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

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

    @Operation(summary = "Get a particular book with the full information of it")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Book found"),
                           @ApiResponse(responseCode = "404", description = "Book not found")})
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@Parameter(description = "id of the book to be searched")
                                           @PathVariable("id")Long id){
        BookDto bookDto = this.bookService.getBook(id);
        if (isNull(bookDto)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BookDto> createBook(@RequestBody BookCreationDto bookCreationDto){
        BookDto bookDto = this.bookService.createBook(bookCreationDto);
        return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(bookDto.getId()).toUri()).body(bookDto);
    }
}
