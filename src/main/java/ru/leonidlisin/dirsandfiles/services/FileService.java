package ru.leonidlisin.dirsandfiles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.leonidlisin.dirsandfiles.beans.FullPathFacade;
import ru.leonidlisin.dirsandfiles.persistence.dto.FileDto;
import ru.leonidlisin.dirsandfiles.persistence.entities.File;
import ru.leonidlisin.dirsandfiles.persistence.entities.FullPath;
import ru.leonidlisin.dirsandfiles.persistence.repositories.FileRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FullPathFacade fullPathFacade;

    public List<FileDto> getFormatedFileList(List<File> fileList){
        String sizeFormatted;
        List<FileDto> fileDtoList = fullPathFacade.getFileDtoList();
        fileDtoList.clear();
        for (File f: fileList){
            if (f.getSize() == -1){
                sizeFormatted = "<DIR>";
            }
            else {
                sizeFormatted = fullPathFacade.formatSize(f.getSize());
            }
            FileDto fileDto = FileDto.builder()
                .name(f.getName())
                .sizeFormatted(sizeFormatted)
                .build();
            fileDtoList.add(fileDto);
        }
        return fileDtoList;
    }

    @Transactional
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
