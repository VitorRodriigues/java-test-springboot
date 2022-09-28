package br.com.apivitor.api.services.impl;

import br.com.apivitor.api.exceptions.DataIntegrityViolationException;
import br.com.apivitor.api.exceptions.ObjectNotFoundException;
import br.com.apivitor.api.model.UserModel;
import br.com.apivitor.api.model.dto.UserDto;
import br.com.apivitor.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "Vitor";
    public static final String EMAIL = "vitor@mail.com";
    public static final String PASSWORD = "123456";
    public static final String OBJECT_NOT_FOUND = "Object Not Found";
    public static final int INDEX = 0;
    public static final String E_MAIL_IS_ALREADY_REGISTERED = "E-mail is already registered";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    private UserModel user;
    private UserDto userDTO;
    private Optional<UserModel> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnUserInstance() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);

        UserModel response = userService.findById(ID);

        assertNotNull(response);
        assertEquals(UserModel.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAndObjectNotFoundException() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));

        try {
            userService.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserModel> response = userService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UserModel.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());

    }

    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserModel response = userService.create(userDTO);

        assertNotNull(response);
        assertEquals(UserModel.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnyDataIntegrityViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2L);
            userService.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(E_MAIL_IS_ALREADY_REGISTERED, ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserModel response = userService.update(userDTO);

        assertNotNull(response);
        assertEquals(UserModel.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnAnyDataIntegrityViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2L);
            userService.update(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(E_MAIL_IS_ALREADY_REGISTERED, ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyLong());;

        userService.delete(ID);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void deleteWithObjectNotFoundException() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));
        try {
            userService.delete(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void findByEmail() {
    }

    private void startUser() {
        user = new UserModel(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDto(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new UserModel(ID, NAME, EMAIL, PASSWORD));
    }
}