package com.sergioramirezme.pinapp.repository;

import com.sergioramirezme.pinapp.model.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client, Integer> {

    @Query("SELECT c.age FROM Client c")
    Page<Integer> findAllAges(Pageable pageable);

}
