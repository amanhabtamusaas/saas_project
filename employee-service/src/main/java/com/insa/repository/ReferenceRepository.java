package com.insa.repository;

import com.insa.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long> {

    List<Reference> findByEmployeeId(Long empId);
}
