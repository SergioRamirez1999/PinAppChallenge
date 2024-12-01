package com.sergioramirezme.pinapp.repository;

import com.sergioramirezme.pinapp.model.entities.AppParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppParameterRepository extends JpaRepository<AppParameter, Integer> {

    AppParameter findByName(String name);

}
