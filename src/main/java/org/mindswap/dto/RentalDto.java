package org.mindswap.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.mindswap.model.Movie;
import org.mindswap.model.User;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
    @NotBlank(message = "Needs a start date")
    private Date startDate;

    @NotBlank(message = "Needs a delivery date")
    private Date endDate;

    @NotBlank(message = "Needs a costumer")
    private User user;

    @NotBlank(message = "Needs movies")
    private List<Movie> movieList;
}
