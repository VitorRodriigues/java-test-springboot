package br.com.apivitor.api.controller;

import br.com.apivitor.api.model.UserModel;
import br.com.apivitor.api.model.dto.UserDto;
import br.com.apivitor.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    public static final long ID = 1L;
    public static final String NAME = "Vitor";
    public static final String EMAIL = "vitor@mail.com";
    public static final String PASSWORD = "123456";
    public static final String OBJECT_NOT_FOUND = "Object Not Found";
    public static final int INDEX = 0;
    public static final String E_MAIL_IS_ALREADY_REGISTERED = "E-mail is already registered";

    @InjectMocks
    private UserController userController;

    @Mock
    private ModelMapper mapper;

    @Mock
    private UserServiceImpl userService;

    private UserModel user;
    private UserDto userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(user);
        Mockito.when((mapper.map(Mockito.any(), Mockito.any()))).thenReturn(userDTO);

        ResponseEntity<UserDto> response = userController.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDto.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
        Mockito.when(userService.findAll()).thenReturn(List.of(user));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<List<UserDto>> response = userController.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDto.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
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

    private void startUser() {
        user = new UserModel(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDto(ID, NAME, EMAIL, PASSWORD);
    }
}