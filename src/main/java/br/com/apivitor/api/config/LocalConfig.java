package br.com.apivitor.api.config;

import br.com.apivitor.api.model.UserModel;
import br.com.apivitor.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void startDB() {
        UserModel u1 = new UserModel(null, "Vitor", "vitor@mail.com", "123");
        UserModel u2 = new UserModel(null, "Pedro", "pedro@mail.com", "123");

        userRepository.saveAll(List.of(u1, u2));
    }
}
