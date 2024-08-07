package com.insa.repository;

import com.insa.entity.Applicant;
import com.insa.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    ExamResult findByApplicantId(Long applicantId);
    boolean existsByApplicant(Applicant applicant);
}
