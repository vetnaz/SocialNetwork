package com.project.socialNetwork.repo;

import com.project.socialNetwork.domain.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Messages,Long> {

}
