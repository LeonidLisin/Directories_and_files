package ru.leonidlisin.dirsandfiles.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.leonidlisin.dirsandfiles.persistence.entities.File;

import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {
}
