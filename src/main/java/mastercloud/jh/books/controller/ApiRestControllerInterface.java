package mastercloud.jh.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mastercloud.jh.books.dto.books.BookCreationDto;
import mastercloud.jh.books.dto.books.BookDto;
import mastercloud.jh.books.dto.books.BookReducedDto;
import mastercloud.jh.books.dto.books.BookWithCommentsDto;
import mastercloud.jh.books.dto.comments.CommentCreationDto;
import mastercloud.jh.books.dto.comments.CommentDto;
import mastercloud.jh.books.dto.users.UserCreationModificationDto;
import mastercloud.jh.books.dto.users.UserDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


public interface ApiRestControllerInterface {

    String APPLICATION_JSON = "application/json";

    @Operation(summary = "Get a reduced information (id, title) of all books")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Books founds",
            content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = BookReducedDto.class)))
    )})
    @GetMapping("/books")
    List<BookReducedDto> getBooks();


    @Operation(summary = "Get a reduced information (id, title) of all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found", content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = CommentDto.class)))),
            @ApiResponse(responseCode = "404", description = "Book not found")})
    @GetMapping("/books/{bookId}/comments")
    List<CommentDto> getBooksComment(@Parameter(description = "id of the book to be searched") @PathVariable("bookId") Long bookId);


    @Operation(summary = "Get a particular book with the full information of it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = BookWithCommentsDto.class))),
            @ApiResponse(responseCode = "404", description = "Book not found")})
    @GetMapping("/books/{bookId}")
    BookWithCommentsDto getBook(@Parameter(description = "id of the book to be searched") @PathVariable("bookId") Long bookId);


    @Operation(summary = "Creates a new book with all the needed ")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Book to be created", required = true,
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = BookCreationDto.class)))
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Book created ok", content = @Content)})
    @PostMapping("/books")
    BookDto createBook(@Parameter(description = "Dto containing all the information of the new book to be created") @Valid @RequestBody BookCreationDto bookCreationDto);


    @Operation(summary = "Deletes a comment according to an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted ok", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    @DeleteMapping("/comments/{commentId}")
    CommentDto deleteComment(@Parameter(description = "Identification of the comment to be deleted")
                             @PathVariable("commentId") Long commentId);


    @Operation(summary = "Creates a new comment with all the needed data ")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Comment to be created", required = true,
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CommentCreationDto.class)))
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Comment created ok",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CommentDto.class)))})
    @PostMapping("/comments")
    CommentDto createComment(@Valid @RequestBody CommentCreationDto commentCreationDto);


    @Operation(summary = "Get information of all users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Users founds",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserDto.class))
    )})
    @GetMapping("/users")
    List<UserDto> getUsers();


    @Operation(summary = "Get a particular user according to an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found")})

    @GetMapping("/users/{userId}")
    UserDto getUser(@Parameter(description = "id of the user to be searched") @PathVariable("userId") Long userId);


    @Operation(summary = "Deletes a user according to an id, this user can not be deleted if has comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted ok", content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "500", description = "User could not be deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/users/{userId}")
    UserDto deleteUser(@Parameter(description = "Identification of the comment to be deleted") @PathVariable("commentId") Long commentId);


    @Operation(summary = "Creates a new user with all the needed data ")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to be created",
            required = true,
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserCreationModificationDto.class)))
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User created ok",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserDto.class)))})
    @PostMapping("/users")
    UserDto createUser(@Valid @RequestBody UserCreationModificationDto userCreationModificationDto);


    @Operation(summary = "Updates the data of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated ok", content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PatchMapping("/users/{userId}")
    UserDto updateUser(@Parameter(description = "id of the user to be updated") @PathVariable("userId") Long userId, @Valid @RequestBody UserCreationModificationDto userCreationModificationDto);
}
