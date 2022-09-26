package br.com.apivitor.api.services;

import br.com.apivitor.api.model.UserModel;

public interface UserService {

    UserModel findById(Long id);
}
