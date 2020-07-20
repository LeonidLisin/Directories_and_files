package ru.leonidlisin.dirsandfiles.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.leonidlisin.dirsandfiles.persistence.dto.FileDto;
import ru.leonidlisin.dirsandfiles.persistence.dto.FullPathDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class FilePathBean {

    private final long kiloByte = 1024L;
    private final long megaByte = 1_048_576L;
    private final long gigaByte = 1_073_741_824L;
    private final long teraByte = 1_099_511_627_776L;

    private List<FullPathDto> fullPathDtoList;
    private List<FileDto> fileDtoList;
    private StringBuilder block;

    @PostConstruct
    public void init(){
        this.fullPathDtoList = new ArrayList<>();
        this.fileDtoList = new ArrayList<>();
        this.block = new StringBuilder();
    }

    public String formatSize(long size){
        double dSize = (double) size;
        String dimension = "б", value = String.valueOf(size);

        if (size >= kiloByte && size < megaByte){
            dimension = "кб";
            value = String.format("%8.2f", dSize/kiloByte);
        }
        if (size >= megaByte && size < gigaByte){
            dimension = "Мб";
            value = String.format("%8.2f", dSize/megaByte);
        }
        if (size >= gigaByte && size < teraByte){
            dimension = "Гб";
            value = String.format("%8.2f", dSize/gigaByte);
        }
        if (size >= teraByte){
            dimension = "Тб";
            value = String.format("%8.2f", dSize/teraByte);
        }
        return value + " " + dimension;
    }
}
