package com.app.faksfit.repository;

import com.app.faksfit.model.Faculty;
import com.app.faksfit.model.Role;
import com.app.faksfit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByUserRole(Role role);

    List<User> findByUserFaculty(Faculty faculty);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

}
