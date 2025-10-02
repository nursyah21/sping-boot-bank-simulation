package com.nurs.backend.repository;

import org.springframework.stereotype.Repository;

import com.nurs.backend.model.Employee;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailId(String emailId);
    Optional<Employee> findByEmailIdAndIdNot(String emailId, Long id);

    @Query(value = "SELECT * FROM employees WHERE email_id = :emailId", nativeQuery = true)
    Optional<Employee> findByEmailIdIgnoringSoftDelete(@Param("emailId") String emailId);
    
    @Query(value = "SELECT * FROM employees WHERE email_id = :emailId AND id != :id", nativeQuery = true)
    Optional<Employee> findByEmailIdAndIdNotIgnoringSoftDelete(
        @Param("emailId") String emailId, 
        @Param("id") Long id
    );

    @Query("SELECT e FROM Employee e " +
        "WHERE LOWER(e.firstName) LIKE LOWER(CONCAT(:keyword, '%')) OR " +
        "LOWER(e.lastName) LIKE LOWER(CONCAT(:keyword, '%')) OR " +
        "LOWER(e.emailId) LIKE LOWER(CONCAT(:keyword, '%')) "
    )
    Page<Employee> search(@Param("keyword") String keyword, Pageable pageable);
}
