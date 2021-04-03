package com.example.testcommonscompress;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 压缩工具类
 */
public class ZipUtil {

    /**
     * 将文件打包成zip压缩包文件
     *
     * @param files               要压缩的文件
     * @param zipFile             压缩文件
     * @param deleteFilesAfterZip 压缩文件后删除原来的文件，临时文件时记得删除
     * @return 是否压缩成功
     */
    public static boolean compress(List<File> files, File zipFile, boolean deleteFilesAfterZip) {
        ZipArchiveOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipArchiveOutputStream(zipFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (zipOutputStream == null) {
            return false;
        }
        //Use Zip64 extensions for all entries where they are required
        zipOutputStream.setUseZip64(Zip64Mode.AsNeeded);
        for (File file : files) {
            InputStream inputStream = null;
            try {
                //将每个文件用ZipArchiveEntry封装，使用ZipArchiveOutputStream写到压缩文件
                ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getName());
                zipOutputStream.putArchiveEntry(zipArchiveEntry);
                inputStream = new FileInputStream(file);
                byte[] buffer = new byte[1024 * 5];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    //把缓冲区的字节写入到ZipArchiveEntry
                    zipOutputStream.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    //关闭输入流
                    if (null != inputStream) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //关闭输出流
        try {
            zipOutputStream.closeArchiveEntry();
            zipOutputStream.finish();
            zipOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //删除源文件
        if (deleteFilesAfterZip) {
            for (File file : files) {
                file.delete();
            }
        }
        return true;
    }

    /**
     * 将zip压缩包解压成文件到指定文件夹
     *
     * @param zipFile      要解压的文件
     * @param targetFolder 解压的目的路径
     * @return 是否成功
     */
    public static boolean decompress(File zipFile, File targetFolder) {
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }
        InputStream inputStream = null;
        //zip文件输入流
        ZipArchiveInputStream zipArchiveInputStream = null;
        ArchiveEntry archiveEntry;
        try {
            inputStream = new FileInputStream(zipFile);
            zipArchiveInputStream = new ZipArchiveInputStream(inputStream,
                    StandardCharsets.UTF_8.name());
            while (null != (archiveEntry = zipArchiveInputStream.getNextEntry())) {
                //获取文件名
                String archiveEntryFileName = archiveEntry.getName();
                //把解压出来的文件写到指定路径
                File entryFile = new File(targetFolder, archiveEntryFileName);
                byte[] buffer = new byte[1024 * 5];
                OutputStream outputStream = new FileOutputStream(entryFile);
                int len;
                while ((len = zipArchiveInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != zipArchiveInputStream) {
                    zipArchiveInputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}