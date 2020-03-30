package com.seven.gwc;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;

@SpringBootTest
public class GwcApplicationTests {

    @Test
    public void contextLoads() {

        File file = new File(ResourceUtils.CLASSPATH_URL_PREFIX + "lawrecord/instrument/01封面_法人.docx");
        System.out.println(file.isFile());
    }

}
