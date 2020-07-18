package ru.leonidlisin.dirsandfiles.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.leonidlisin.dirsandfiles.persistence.dto.FullPathDto;
import ru.leonidlisin.dirsandfiles.services.FullPathService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final FullPathService fullPathService;

    @GetMapping
    public String index(){
        return "index";
    }

    @PostMapping("/addir")
    public void addDirectoryPath(FullPathDto fullPathDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fullPathService.save(fullPathDto);
        response.sendRedirect(request.getHeader("referer"));
    }

}
