package org.example.fp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.fp.enums.OperationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
@Data
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column( nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type; //put, take

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Operation() {}

    public Operation(Integer userId, Double amount, OperationType type) {
        this.userId = userId;
        this.amount = amount;
        this.type = type;
    }

}
