package ru.leonidlisin.dirsandfiles.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.leonidlisin.dirsandfiles.persistence.dto.FullPathDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class FullPathFacade {

    private List<FullPathDto> fullPathList;

    @PostConstruct
    public void init(){
        this.fullPathList = new ArrayList<>();
    }

    public String formatSize(long size){
        double dSize = (double) size;
        String dimension = "б", value = String.valueOf(size);

        if (size >= 1024L && size < 1_048_576L){
            dimension = "кб";
            value = String.format("%8.2f", dSize/1024L);
        }
        if (size >= 1_048_576L && size < 1_073_741_824L){
            dimension = "Мб";
            value = String.format("%8.2f", dSize/1_048_576L);
        }
        if (size >= 1_073_741_824L && size < 1_099_511_627_776L){
            dimension = "Гб";
            value = String.format("%8.2f", dSize/1_073_741_824L);
        }
        if (size >= 1_099_511_627_776L && size < 1_125_899_906_842_624L){
            dimension = "Тб";
            value = String.format("%8.2f", dSize/1_099_511_627_776L);
        }
        return value + dimension;
    }
}
