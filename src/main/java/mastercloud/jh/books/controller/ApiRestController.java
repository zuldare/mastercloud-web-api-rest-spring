package mastercloud.jh.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mastercloud.jh.books.dto.*;
import mastercloud.jh.books.exception.NotFoundException;
import mastercloud.jh.books.service.BookService;
import mastercloud.jh.books.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    private static final String APPLICATION_JSON = "application/json";
    private final BookService bookService;
    private final CommentService commentService;

    public ApiRestController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @Operation(summary = "Get a reduced information (id, title) of all books")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Books founds",
                 content = {@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = BookReducedDto.class))}
    )})
    @GetMapping("/books")
    public List<BookReducedDto> getBooks() {
        return this.bookService.getBooks();
    }


    @Operation(summary = "Get a particular book with the full information of it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found",
                         content = {@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = BookDto.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found")})

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookDto> getBook(@Parameter(description = "id of the book to be searched")
                                           @PathVariable("bookId") Long bookId) {
        BookDto bookDto = this.bookService.getBook(bookId);
        if (isNull(bookDto)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }


    @Operation(summary = "Creates a new book with all the needed ")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Book created ok",
            content = {@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = BookDto.class))})})
    @PostMapping("/books")
    public ResponseEntity<BookDto> createBook(@Parameter(description = "Dto containing all the information of the new book to be created")
                                              @Valid @RequestBody BookCreationDto bookCreationDto) {
        BookDto bookDto = this.bookService.createBook(bookCreationDto);
        return ResponseEntity.created(fromCurrentRequest().path("/books/{bookId}").buildAndExpand(bookDto.getId()).toUri()).body(bookDto);
    }



    @Operation(summary = "Deletes a comment according to an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted ok", content = {@Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = CommentDto.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found", content = {@Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = CommentDto.class))})
    })
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> delete(@Parameter(description = "Identification of the comment to be deleted")
                                          @PathVariable("commentId") Long commentId) {
        CommentDto commentDto;
        try {
            commentDto = this.commentService.deleteComment(commentId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentDto);
    }


    @Operation(summary = "Creates a new comment with all the needed data ")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Comment created ok",
            content = {@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CommentDto.class))})})
    @PostMapping("/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentCreationDto commentCreationDto){
        CommentDto commentDto = this.commentService.createComment(commentCreationDto);
        return ResponseEntity.created(fromCurrentRequest().path("/comment").buildAndExpand(commentDto.getId()).toUri()).body(commentDto);
    }
}
