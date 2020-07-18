package ru.leonidlisin.dirsandfiles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.leonidlisin.dirsandfiles.persistence.dto.FullPathDto;
import ru.leonidlisin.dirsandfiles.persistence.entities.FullPath;
import ru.leonidlisin.dirsandfiles.persistence.repositories.FullPathRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FullPathService {

    private final FullPathRepository fullPathRepository;

    public List<FullPath> getFileList(){
        return fullPathRepository.findAll();
    }

    @Transactional
    public void save(FullPathDto fullPathDto){
        FullPath fullPath = FullPath.builder()
                .fullPath(fullPathDto.getFullPath())
                .date(new Date())
                .build();

        fullPathRepository.save(fullPath);
    }

}
