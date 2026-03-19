package com.aegiscapital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name ="transactions",
        uniqueConstraints = @UniqueConstraint(columnNames = "transaction_id")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status; // to check whether transaction success or failed

    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime timestamp;  // to display when the transaction is done

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;  // account sending money

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;  // account receiving money

    private String fromAccountNumber;

    private String toAccountNumber;



}
