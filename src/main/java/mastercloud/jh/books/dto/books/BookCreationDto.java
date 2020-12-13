package mastercloud.jh.books.dto.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCreationDto {

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String summary;

    @NotNull
    private String publishingHouse;

    @NotNull
    @Max(2099)
    @Min(0)
    private Integer publishYear;

}
