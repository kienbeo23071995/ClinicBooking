package com.example.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.be.entities.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String>, CrudRepository<Attachment, String>{

}
