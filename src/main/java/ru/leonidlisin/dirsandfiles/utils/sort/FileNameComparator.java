package ru.leonidlisin.dirsandfiles.utils.sort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leonidlisin.dirsandfiles.beans.FilePathBean;

import java.nio.file.Path;
import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class FileNameComparator implements Comparator<Path> {

    private final FilePathBean filePathBean;

    private boolean isDigit(char ch)
    {
        return ((ch >= 48) && (ch <= 57));
    }

    private String getBlock(String s, int slength, int marker)
    {
        StringBuilder block = filePathBean.getBlock();
        block.setLength(0);
        char c = s.charAt(marker);
        block.append(c);
        marker++;
        if (isDigit(c))
        {
            while (marker < slength)
            {
                c = s.charAt(marker);
                if (!isDigit(c))
                    break;
                block.append(c);
                marker++;
            }
        } else
        {
            while (marker < slength)
            {
                c = s.charAt(marker);
                if (isDigit(c) || c == '.')
                    break;
                block.append(c);
                marker++;
            }
        }
        return block.toString();
    }

    public int compare(Path p1, Path p2)
    {
        String s1 = p1.getFileName().toString();
        String s2 = p2.getFileName().toString();
        if ((s1 == null) || (s2 == null))
        {
            return 0;
        }

        int thisMarker = 0;
        int thatMarker = 0;
        int s1Length = s1.length();
        int s2Length = s2.length();

        while (thisMarker < s1Length && thatMarker < s2Length)
        {
            String thisBlock = getBlock(s1, s1Length, thisMarker);
            thisMarker += thisBlock.length();

            String thatBlock = getBlock(s2, s2Length, thatMarker);
            thatMarker += thatBlock.length();


            int result;
            if (isDigit(thisBlock.charAt(0)) && isDigit(thatBlock.charAt(0)))
            {
                result = Integer.parseInt(thisBlock) - Integer.parseInt(thatBlock);
            }
            else
            {
                result = thisBlock.toLowerCase().compareTo(thatBlock.toLowerCase());
            }

            if (result != 0)
                return result;
        }

        return s1Length - s2Length;
    }



}
