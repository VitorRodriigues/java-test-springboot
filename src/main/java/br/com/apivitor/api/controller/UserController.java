package br.com.apivitor.api.controller;

import br.com.apivitor.api.model.dto.UserDto;
import br.com.apivitor.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(userService.findById(id), UserDto.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll().stream().map(x -> mapper.map(x, UserDto.class)).toList());
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto user) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/findById/{id}").buildAndExpand(userService.create(user).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
