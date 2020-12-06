package mastercloud.jh.books.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    private String user;
}
