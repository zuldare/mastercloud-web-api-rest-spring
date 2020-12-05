package mastercloud.jh.books.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private Integer score;
    private String commentary;
    private String name;
}
