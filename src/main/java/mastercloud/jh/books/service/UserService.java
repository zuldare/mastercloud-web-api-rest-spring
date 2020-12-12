package mastercloud.jh.books.service;

import mastercloud.jh.books.dto.UserCreationModificationDto;
import mastercloud.jh.books.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto updateUser(Long userId, UserCreationModificationDto userCreationModificationDto);

    UserDto createUser(UserCreationModificationDto userCreationModificationDto);

    UserDto deleteUser(Long userId);

    UserDto getUser(Long userId);

    List<UserDto> getUsers();
}
