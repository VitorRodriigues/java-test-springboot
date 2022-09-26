package br.com.apivitor.api.services;

import br.com.apivitor.api.model.UserModel;
import br.com.apivitor.api.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserModel findById(Long id);

    List<UserModel> findAll();

    UserModel create(UserDto user);
}
