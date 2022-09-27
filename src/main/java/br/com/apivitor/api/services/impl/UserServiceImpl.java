package br.com.apivitor.api.services.impl;

import br.com.apivitor.api.exceptions.DataIntegrityViolationException;
import br.com.apivitor.api.exceptions.ObjectNotFoundException;
import br.com.apivitor.api.model.UserModel;
import br.com.apivitor.api.model.dto.UserDto;
import br.com.apivitor.api.repository.UserRepository;
import br.com.apivitor.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel findById(Long id) {
        Optional<UserModel> userModel = this.userRepository.findById(id);

        return userModel.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserModel create(UserDto user) {
        findByEmail(user);
        return userRepository.save(mapper.map(user, UserModel.class));
    }

    public void findByEmail(UserDto userDTO) {
        Optional<UserModel> user = userRepository.findByEmail(userDTO.getEmail());

        if (user.isPresent()) {
            throw new DataIntegrityViolationException("E-mail is already registered");
        }
    }
}
