package com.nurs.backend.model;


import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE transactions SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
@Table(name = "transactions", indexes = {
  @Index(name = "idx_created_at", columnList = "createdAt"),
  @Index(name = "idx_is_deleted", columnList = "isDeleted")
})
public class Transaction extends BaseModel{
    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id", nullable = false)
    private Account destinationAccount; 
}
