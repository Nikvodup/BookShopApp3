package com.example.data;


import com.example.security.BookstoreUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "book2user")
public class Book2User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book2user_book"))
    private  Book book;

    @ManyToOne(optional=false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_book2user_user"))
    private BookstoreUser user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_type_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book2user_type"))
    private com.example.data.Book2Type book2Type;

}
