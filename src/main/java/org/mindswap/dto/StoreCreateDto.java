package org.mindswap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreateDto {
    @NotBlank(message = "Must have a city")
    private String city;

    @NotBlank(message = "Must have an address")
    private String address;

}
