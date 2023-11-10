package com.example.be.services.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.be.entities.Degree;
import com.example.be.repository.DegreeRepository;
import com.example.be.services.DegreeService;

@Service
public class DegreeServiceImpl implements DegreeService{

	@Autowired
	DegreeRepository degreeRepository;
	
	@Override
	public Set<Degree> getById(List<String> ids) {
		return degreeRepository.findByIdIn(ids);
	}

	@Override
	public List<Degree> getAll() {
		return  degreeRepository.findAll();
	}

}
