package mastercloud.jh.books.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationModificationDto {

    @NotNull
    private String nick;

    @NotNull
    @Email
    private String email;
}
