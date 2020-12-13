package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.users.UserCreationModificationDto;
import mastercloud.jh.books.dto.users.UserDto;
import mastercloud.jh.books.exception.UserHasCommentsException;
import mastercloud.jh.books.exception.UserNickAlreadyExistsException;
import mastercloud.jh.books.exception.UserNotFoundException;
import mastercloud.jh.books.model.User;
import mastercloud.jh.books.repository.UserRepository;
import mastercloud.jh.books.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

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
        User userById = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        checkIfNickAlreadyExists(userId, userCreationModificationDto.getNick());

        userById.setEmail(userCreationModificationDto.getEmail());
        userById.setNick(userCreationModificationDto.getNick());
        userById = this.userRepository.save(userById);

        log.info("Saved user was: {}", userById);
        return modelMapper.map(userById, UserDto.class);
    }

    private void checkIfNickAlreadyExists(Long userId, String nick){
        Optional<User> userOptional = this.userRepository.findByNick(nick);
        if (userOptional.isPresent() && userOptional.get().getId().longValue() != userId.longValue()){
            log.error("Another user with nick: {} exists", nick);
            throw new UserNickAlreadyExistsException();
        }
    }

    @Override
    public UserDto createUser(UserCreationModificationDto userCreationModificationDto) {
        log.info("Create user from: {}", userCreationModificationDto);

        if (this.userRepository.existsByNick(userCreationModificationDto.getNick())){
            log.error("Another user with nick: {} exists", userCreationModificationDto.getNick());
            throw new UserNickAlreadyExistsException();
        }

        User user = this.userRepository.save(modelMapper.map(userCreationModificationDto, User.class));
        log.info("User created was: {}", user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto deleteUser(Long userId) {
        log.info("Deleting user with id: {}", userId);
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (!CollectionUtils.isEmpty(user.getComments())) {
            log.info("User with id: {} has comments therefore it can not be deleted", userId);
            throw new UserHasCommentsException();
        }

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
        List<UserDto> userDtos = modelMapper.map(this.userRepository.findAll(), new TypeToken<List<UserDto>>() {
        }.getType());
        log.info("Returned users where {}", userDtos);
        return userDtos;
    }


}
