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
}
