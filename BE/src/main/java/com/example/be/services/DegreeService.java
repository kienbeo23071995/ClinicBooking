package com.example.be.services;

import java.util.List;
import java.util.Set;

import com.example.be.entities.Degree;

public interface DegreeService {
	Set<Degree> getById(List<String> ids);
	
	List<Degree> getAll();
}
