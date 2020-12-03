package mastercloud.jh.books.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class BookCreationDto {

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String author;

    @NotNull
    @NotEmpty
    private String summary;

    @NotNull
    @NotEmpty
    private String publishingHouse;

    @NotNull
    @Max(2099)
    @Min(0)
    private Integer publishYear;

}
