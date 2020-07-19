package ru.leonidlisin.dirsandfiles.persistence.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullPathDto {
    private UUID id;
    private String fullPath;
    private Date date;
    private int filesCount;
    private int dirsCount;
    private long summarySize;
}
