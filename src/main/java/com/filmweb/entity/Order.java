package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nchar(8)")
    private String vnp_TxnRef;

    @Column(columnDefinition = "nchar(30)")
    private String vnp_OrderInfo;

    @Column(columnDefinition = "nchar(2)")
    private String vnp_ResponseCode;

    @Column(columnDefinition = "nchar(8)")
    private String vnp_TransactionNo;

    @Column(columnDefinition = "nchar(20)")
    private String vnp_BankCode;

    private Long vnp_Amount;

    @Column(columnDefinition = "datetime")
    private Timestamp vnp_PayDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
