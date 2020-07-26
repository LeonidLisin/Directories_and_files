package ru.leonidlisin.dirsandfiles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.leonidlisin.dirsandfiles.beans.FilePathBean;
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
    private final FilePathBean filePathBean;

    public List<FileDto> getFormatedFileList(List<File> fileList){
        String sizeFormatted;
        List<FileDto> fileDtoList = filePathBean.getFileDtoList();
        fileDtoList.clear();
        for (File f: fileList){
            sizeFormatted = f.getSize() == -1 ? "<DIR>" : filePathBean.formatSize(f.getSize());
            FileDto fileDto = FileDto.builder()
                    .name(f.getName())
                    .sizeFormatted(sizeFormatted)
                    .build();
            fileDtoList.add(fileDto);
        }
        return fileDtoList;
    }

    @Transactional
    public void save(List<Path> pathes, FullPath fullPath) {
        for(Path p: pathes){
            boolean isDir = Files.isDirectory(p);
            long size = -1;
            if (!isDir) {
                try {
                    FileChannel fileChannel = FileChannel.open(p);
                    size = fileChannel.size();
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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