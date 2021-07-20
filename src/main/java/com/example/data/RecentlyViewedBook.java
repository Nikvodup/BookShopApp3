package com.example.data;

import com.example.security.BookstoreUser;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "recently_viewed")
public class RecentlyViewedBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_recently_viewed_user"))
    private BookstoreUser user;

    @ManyToOne()
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_recently_viewed_book"))
    private Book book;

    @Column(name="last_veiw_date_time")
    private Timestamp time;

}
