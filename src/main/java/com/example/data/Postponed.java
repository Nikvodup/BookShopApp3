package com.example.data;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "postponed")
public class Postponed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    private String author;
    private String title;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName="id")
    private Customer customer;
}
