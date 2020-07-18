package ru.leonidlisin.dirsandfiles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.leonidlisin.dirsandfiles.persistence.entities.File;
import ru.leonidlisin.dirsandfiles.persistence.entities.FullPath;
import ru.leonidlisin.dirsandfiles.persistence.repositories.FileRepository;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public void save(List<Path> pathes, FullPath fullPath) throws IOException {
        for(Path p: pathes){
            boolean isDir = Files.isDirectory(p);
            long size = -1;
            if (!isDir) size = FileChannel.open(p).size();
            fileRepository.save(
                    File.builder()
                    .name(p.getFileName().toString())
                    .size(size)
                    .fullPath(fullPath)
                    .isDir(isDir)
                    .build()
            );
        }
    }

}
