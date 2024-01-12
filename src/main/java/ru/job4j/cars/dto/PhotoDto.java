package ru.job4j.cars.dto;

import lombok.*;

@AllArgsConstructor
@Data
public class PhotoDto {
    private String path;
    private byte[] content;
}
