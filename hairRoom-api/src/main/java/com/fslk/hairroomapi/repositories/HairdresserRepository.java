package com.fslk.hairroomapi.repositories;

import com.fslk.hairroomapi.entities.Hairdresser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HairdresserRepository extends JpaRepository<Hairdresser, Long> {

}
