package com.filmweb.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

import java.sql.Timestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.hibernate.type.descriptor.jdbc.NCharJdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "orders")
public class Order {
    
    @Id @GeneratedValue @JdbcType(VarcharJdbcType.class)
    private UUID id;
    
    @Column(length = 8) @JdbcType(NCharJdbcType.class)
    private String vnp_TxnRef;
    
    @Column(length = 30) @JdbcType(NCharJdbcType.class)
    private String vnp_OrderInfo;
    
    @Column(length = 2) @JdbcType(NCharJdbcType.class)
    private String vnp_ResponseCode;
    
    @Column(length = 8) @JdbcType(NCharJdbcType.class)
    private String vnp_TransactionNo;
    
    @Column(length = 20) @JdbcType(NCharJdbcType.class)
    private String vnp_BankCode;
    
    @JdbcType(BigIntJdbcType.class)
    private Long vnp_Amount;
    
    @JdbcType(TimestampJdbcType.class)
    private Timestamp vnp_PayDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    /* Audit field */
    @Column(name = "created_at") @JdbcType(TimestampJdbcType.class)
    private LocalDateTime createdAt;
   
    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
