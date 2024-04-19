package com.example.Course.Registration.Services;

// import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


import com.example.Course.Registration.models.User;
import com.example.Course.Registration.repository.UserRepository;

public interface UserService {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User save(User user);
    
}
