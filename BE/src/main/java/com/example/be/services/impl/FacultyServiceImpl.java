package com.example.be.services.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.be.entities.Faculty;
import com.example.be.repository.FacultyRepository;
import com.example.be.services.FacultyService;

@Service
public class FacultyServiceImpl implements FacultyService{

	@Autowired
	FacultyRepository facultyRepository;
	
	@Override
	public Set<Faculty> getById(List<String> ids) {
		return facultyRepository.findByIdIn(ids);
	}

	@Override
	public List<Faculty> getAll() {
		return facultyRepository.findAll();
	}

}
