package com.example.testcommonscompress;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        File f1 = new File("C:\\Users\\thedoflin\\Downloads\\testcommonscompress\\pom.xml1");
        File f2 = new File("C:\\Users\\thedoflin\\Downloads\\testcommonscompress\\pom.xml2");
        File f3 = new File("C:\\Users\\thedoflin\\Downloads\\testcommonscompress\\pom.xml3");
        File f4 = new File("C:\\Users\\thedoflin\\Downloads\\testcommonscompress\\pom.xml4");

        List<File> files = Arrays.asList(f1, f2, f3, f4);
        File zipFile = new File("C:\\Users\\thedoflin\\Downloads\\testcommonscompress\\t.zip");
        // 压缩文件
        ZipUtil.compress(files, zipFile, true);

        // 解压缩文件
//        ZipUtil.decompress(zipFile, new File("/Users/zhangbaozhen/my/hahaha/"));
    }
}
