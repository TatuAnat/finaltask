package org.example.fp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Double balance;

    public Account() {}

    public Account(Integer userId, Double balance) {
        this.userId = userId;
        this.balance = balance;
    }


    

}
