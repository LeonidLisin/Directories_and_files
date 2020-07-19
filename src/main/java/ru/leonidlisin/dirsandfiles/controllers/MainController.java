package ru.leonidlisin.dirsandfiles.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.leonidlisin.dirsandfiles.persistence.dto.FullPathDto;
import ru.leonidlisin.dirsandfiles.persistence.entities.File;
import ru.leonidlisin.dirsandfiles.services.FullPathService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final FullPathService fullPathService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("fullPathList", fullPathService.obtainFullPathList());
        return "index";
    }

    @PostMapping("/addir")
    public void addDirectoryPath(FullPathDto fullPathDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fullPathService.save(fullPathDto);
        response.sendRedirect(request.getHeader("referer"));
    }

    @PostMapping("/files/{id}")
    public String getFiles(@PathVariable UUID id, Model model) {
        model.addAttribute("title", fullPathService.getFullPathById(id).getFullPath());
        model.addAttribute("fileList", fullPathService.getFullPathById(id).getFiles());
        return "files";
    }

}
