package mastercloud.jh.books.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
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
