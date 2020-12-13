package mastercloud.jh.books.dto.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mastercloud.jh.books.dto.comments.CommentDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String summary;
    private String publishingHouse;
    private Integer publishYear;
    private List<CommentDto> comments;
}
