package br.com.apivitor.api.services.impl;

import br.com.apivitor.api.model.UserModel;
import br.com.apivitor.api.repository.UserRepository;
import br.com.apivitor.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel findById(Long id) {
        Optional<UserModel> userModel = this.userRepository.findById(id);

        return userModel.orElse(null);
    }
}
