package com.nurs.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nurs.backend.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t From Transaction t " +
        "JOIN FETCH t.sourceAccount sa " +
        "JOIN FETCH t.destinationAccount da " +
        "WHERE ( CAST(t.amount as string) LIKE CONCAT(:keyword, '%') OR " +
        "LOWER(sa.accountId) LIKE LOWER(CONCAT(:keyword, '%')) OR " +
        "LOWER(da.accountId) LIKE LOWER(CONCAT(:keyword, '%')) )"
    )
    Page<Transaction> searchByAdmin(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT t From Transaction t " +
        "JOIN FETCH t.sourceAccount sa " +
        "JOIN FETCH t.destinationAccount da " +
        "WHERE (sa.accountId = :accountId OR da.accountId = :accountId) AND" +
        "( CAST(t.amount as string) LIKE CONCAT(:keyword, '%') OR " +
        "LOWER(sa.accountId) LIKE LOWER(CONCAT(:keyword, '%')) OR " +
        "LOWER(da.accountId) LIKE LOWER(CONCAT(:keyword, '%')) )"
    )
    Page<Transaction> searchByAccountId(
      @Param("keyword") String keyword, Pageable pageable, @Param("accountId") String accountId
    );
}
