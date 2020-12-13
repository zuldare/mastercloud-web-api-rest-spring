package mastercloud.jh.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "User nick already exists")
public class UserNickAlreadyExistsException extends RuntimeException {
}
