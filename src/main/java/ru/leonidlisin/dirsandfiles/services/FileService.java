package ru.leonidlisin.dirsandfiles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.leonidlisin.dirsandfiles.persistence.repositories.FileRepository;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

}
