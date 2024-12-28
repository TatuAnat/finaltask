package org.example.fp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.fp.enums.OperationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Data
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column( nullable = false)
    private Integer fromUserId;

    @Column( nullable = false)
    private Integer toUserId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}
