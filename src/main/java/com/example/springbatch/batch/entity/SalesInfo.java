package com.example.springbatch.batch.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "sales_info")
public class SalesInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String product;
    private String seller;
    private Integer sellerId;
    private double price;
    private String city;
    private String category;
}
