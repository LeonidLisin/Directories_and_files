package ru.leonidlisin.dirsandfiles.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.leonidlisin.dirsandfiles.persistence.entities.FullPath;

import java.util.UUID;

public interface FullPathRepository extends JpaRepository<FullPath, UUID> {
}
