package com.example.testcommonscompress;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\thedoflin\\Downloads\\testcommonscompress\\";
        File f1 = new File(folderPath + "pom.xml1");
        File f2 = new File(folderPath + "pom.xml2");
        File f3 = new File(folderPath + "pom.xml3");
        File f4 = new File(folderPath + "pom.xml4");

        List<File> files = Arrays.asList(f1, f2, f3, f4);
        File zipFile = new File(folderPath + "t.zip");

//        ZipUtil.compress(files, zipFile, true);
        System.out.println(ZipUtil.decompress(zipFile, zipFile.getParentFile()));
    }
}
