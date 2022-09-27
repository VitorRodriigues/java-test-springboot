package br.com.apivitor.api.services.impl;

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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "Vitor";
    public static final String EMAIL = "vitor@mail.com";
    public static final String PASSWORD = "123456";
    public static final String OBJECT_NOT_FOUND = "Object Not Found";

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
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
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