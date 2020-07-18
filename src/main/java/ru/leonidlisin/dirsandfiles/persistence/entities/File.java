package ru.leonidlisin.dirsandfiles.persistence.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "file_tbl")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class File {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private long size;

    @Column(name = "is_dir")
    private boolean isDir;

    @ManyToOne
    @JoinColumn(name = "full_path")
    private FullPath fullPath;
}
