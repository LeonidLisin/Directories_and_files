package ru.leonidlisin.dirsandfiles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.leonidlisin.dirsandfiles.persistence.dto.FullPathDto;
import ru.leonidlisin.dirsandfiles.persistence.entities.FullPath;
import ru.leonidlisin.dirsandfiles.persistence.repositories.FullPathRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FullPathService {

    private final FullPathRepository fullPathRepository;
    private final FileService fileService;

    public List<FullPath> getFileListFromDb(){
        return fullPathRepository.findAll();
    }

    @Transactional
    public void save(FullPathDto fullPathDto) throws IOException {
        FullPath fullPath = FullPath.builder()
                .fullPath(fullPathDto.getFullPath())
                .date(new Date())
                .build();

        fullPathRepository.save(fullPath);
        fileService.save(getDirContent(fullPathDto.getFullPath()), fullPath);
    }

    private List<Path> getDirContent(String fullPath) throws IOException {
        List<Path> folders =  Files.list(Paths.get(fullPath))
                .filter(p -> Files.isDirectory(p))
                .sorted((Comparator.naturalOrder()))
                .collect(Collectors.toList());

        List<Path> files =  Files.list(Paths.get(fullPath))
                .filter(p -> !Files.isDirectory(p))
                .sorted((Comparator.naturalOrder()))
                .collect(Collectors.toList());
        List<Path> all = new ArrayList<>(folders);
        all.addAll(files);
        return all;
    }

}
