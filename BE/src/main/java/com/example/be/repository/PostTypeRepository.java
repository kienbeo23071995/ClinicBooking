package com.example.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.be.entities.PostType;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType, String>, CrudRepository<PostType, String> {

}
