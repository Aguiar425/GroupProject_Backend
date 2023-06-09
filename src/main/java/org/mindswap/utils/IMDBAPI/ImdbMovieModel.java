package org.mindswap.utils.IMDBAPI;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "moviesimdb")
@SQLDelete(sql = "UPDATE movies SET deleted = true WHERE id=?")
@Where(clause = "deleted_moviemodel=false")
public class ImdbMovieModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imdbId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double imdbRating;

    @Column
    private String movieGenre;

    @Column
    private String imageCoverUrl;

    @Column(nullable = false)
    private boolean deletedMoviemodel = Boolean.FALSE;

}
