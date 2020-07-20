package ru.leonidlisin.dirsandfiles;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.leonidlisin.dirsandfiles.beans.FilePathBean;
import ru.leonidlisin.dirsandfiles.utils.sort.FileNameComparator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortTest {

    private List<String> fileNames, sortedFileNames;

    @Before
    public void init() throws IOException, URISyntaxException {

        FilePathBean filePathBean = new FilePathBean();
        filePathBean.init();
        Comparator<Path> comparator = new FileNameComparator(filePathBean);

        List<Path> folders =  Files.list(Paths.get(ClassLoader.getSystemResource("123").toURI()))
                .filter(p -> Files.isDirectory(p))
                .sorted((comparator))
                .collect(Collectors.toList());

        List<Path> files =  Files.list(Paths.get(ClassLoader.getSystemResource("123").toURI()))
                .filter(p -> !Files.isDirectory(p))
                .sorted((comparator))
                .collect(Collectors.toList());
        List<Path> all = new ArrayList<>(folders);
        all.addAll(files);
        fileNames = all.stream()
                .map(p -> p.getFileName().toString())
                .collect(Collectors.toList());

        sortedFileNames = Arrays.asList(
                "innerTemp",
                "X-FILES",
                "f.txt",
                "F1.txt",
                "f4_99.JPG",
                "F4_00127.pdf",
                "f0008.doc",
                "function.cpp",
                "G012__f00013__0015gUU.kkk",
                "G012__f00013__0016gUU.txt",
                "g12m13ff15ggg.txt"
        );
    }

    @Test()
    public void sortingTest (){
        Assert.assertEquals(sortedFileNames, fileNames);
    }
}
