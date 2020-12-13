package mastercloud.jh.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "User has comments and can not be deleted")
public class UserHasCommentsException extends RuntimeException {
}
