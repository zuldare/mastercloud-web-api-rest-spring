package mastercloud.jh.books.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreationDto {

    @NotNull
    @Max(5)
    @Min(0)
    private Integer score;

    @NotNull
    @NotEmpty
    private String commentary;

    @NotNull
    @NotEmpty
    private String author;

    @NotNull
    private Long bookId;
}
