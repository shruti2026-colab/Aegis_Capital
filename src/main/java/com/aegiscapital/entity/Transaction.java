package com.aegiscapital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name ="transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status; // to check whether transaction success or failed

    @Column(name = "transaction_time", nullable = false)
    private Timestamp timestamp;  // to display when the transaction is done

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;  // account sending money

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;  // account receiving money



}
