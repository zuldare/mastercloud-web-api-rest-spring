package mastercloud.jh.books.dto.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mastercloud.jh.books.dto.comments.CommentReducedDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookWithCommentsDto {
    private Long id;
    private String title;
    private String author;
    private String summary;
    private String publishingHouse;
    private Integer publishYear;
    private List<CommentReducedDto> comments;
}
