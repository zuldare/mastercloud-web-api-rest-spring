package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.UserCreationModificationDto;
import mastercloud.jh.books.dto.UserDto;
import mastercloud.jh.books.exception.UserNotFoundException;
import mastercloud.jh.books.model.User;
import mastercloud.jh.books.repository.UserRepository;
import mastercloud.jh.books.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto updateUser(Long userId, UserCreationModificationDto userCreationModificationDto) {
        log.info("Updating user with userId: {} with: {}", userId, userCreationModificationDto);
        User user = getUpdatedUser(userCreationModificationDto);
        log.info("Saved user was: {}", user);
        return modelMapper.map(user, UserDto.class);
    }

    private User getUpdatedUser(UserCreationModificationDto userCreationModificationDto) {
        User user = this.userRepository.save(modelMapper.map(userCreationModificationDto, User.class));
        user.setNick(userCreationModificationDto.getNick());
        user.setEmail(userCreationModificationDto.getEmail());
        user = this.userRepository.save(user);
        return user;
    }

    @Override
    public UserDto createUser(UserCreationModificationDto userCreationModificationDto) {
        log.info("Create user from: {}", userCreationModificationDto);
        User user = this.userRepository.save(modelMapper.map(userCreationModificationDto, User.class));
        log.info("User created was: {}", user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto deleteUser(Long userId) {
        log.info("Deleting user with id: {}", userId);
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        this.userRepository.delete(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUser(Long userId) {
        log.info("Get user with userId: {}", userId);
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        log.info("User obtained was {}", user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getUsers() {
        log.info("Getting all users");
        List<UserDto> userDtos = modelMapper.map(this.userRepository.findAll(), new TypeToken<List<UserDto>>(){}.getType());
        log.info("Returned users where {}", userDtos);
        return userDtos;
    }
}
