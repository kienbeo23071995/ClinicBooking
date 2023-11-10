package com.example.be.services;

import java.util.List;
import java.util.Set;

import com.example.be.entities.Faculty;

public interface FacultyService {
	Set<Faculty> getById(List<String> ids);
	List<Faculty> getAll();
}
