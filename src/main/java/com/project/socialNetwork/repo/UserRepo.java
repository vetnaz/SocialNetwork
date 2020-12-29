package com.project.socialNetwork.repo;

import com.project.socialNetwork.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {

}
