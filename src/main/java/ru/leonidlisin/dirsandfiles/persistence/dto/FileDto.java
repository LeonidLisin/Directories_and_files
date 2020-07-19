package ru.leonidlisin.dirsandfiles.persistence.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String name;
    private String sizeFormatted;
}
