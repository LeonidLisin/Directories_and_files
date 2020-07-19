package ru.leonidlisin.dirsandfiles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.leonidlisin.dirsandfiles.beans.FilePathBean;
import ru.leonidlisin.dirsandfiles.persistence.dto.FullPathDto;
import ru.leonidlisin.dirsandfiles.persistence.entities.FullPath;
import ru.leonidlisin.dirsandfiles.persistence.repositories.FullPathRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FullPathService {

    private final FullPathRepository fullPathRepository;
    private final FileService fileService;
    private final FilePathBean filePathBean;

    public List<FullPath> getAllFullPathes(){
        return fullPathRepository.findAll();
    }

    public FullPath getFullPathById(UUID id){
        return fullPathRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No such path found")
        );
    }

    public List<FullPathDto> obtainFullPathList(){
        List<FullPathDto> list = filePathBean.getFullPathDtoList();
        list.clear();
        for(FullPath fp: getAllFullPathes()){
            list.add(obtainDirInfo(fp));
        }
        return list;
    }

    public FullPathDto obtainDirInfo(FullPath fullPath){

        FullPathDto fullPathDto = FullPathDto.builder()
                .id(fullPath.getId())
                .fullPath(fullPath.getFullPath())
                .date(fullPath.getDate())
                .dateFormatted(
                    new SimpleDateFormat("d.MM.y k.mm", Locale.getDefault()).format(fullPath.getDate())
                )
                .build();

        Path rootPath = Paths.get(fullPath.getFullPath());

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
                int filesCount = 0, dirsCount = 0;
                long summarySize = 0;

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    filesCount++;
                    summarySize += FileChannel.open(file).size();
                    fullPathDto.setFilesCount(filesCount);
                    fullPathDto.setSummarySize(summarySize);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException ex) {
                    dirsCount++;
                    fullPathDto.setDirsCount(dirsCount);
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e){
            e.printStackTrace();
        }
        fullPathDto.setDirsCount(fullPathDto.getDirsCount()-1);
        fullPathDto.setSummarySizeFormatted(filePathBean.formatSize(fullPathDto.getSummarySize()));
        return fullPathDto;
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

    public boolean isPathValid(FullPathDto fullPathDto){
        return Files.exists(Paths.get(fullPathDto.getFullPath()));
    }

    @Transactional
    public void save(FullPathDto fullPathDto) throws IOException {

        if (!isPathValid(fullPathDto)){
            return;
        }

        for (FullPath fp: getAllFullPathes()){
            if (fp.getFullPath().toLowerCase().equals(fullPathDto.getFullPath().toLowerCase())){
                return;
            }
        }

        FullPath fullPath = FullPath.builder()
                .fullPath(fullPathDto.getFullPath())
                .date(new Date())
                .build();

        fullPathRepository.save(fullPath);
        fileService.save(getDirContent(fullPathDto.getFullPath()), fullPath);
    }

}
