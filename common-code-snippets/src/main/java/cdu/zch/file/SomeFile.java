package cdu.zch.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 一些文件操作
 *
 * @author Zch
 * @data 2023/6/18
 **/
public class SomeFile {

    // 列出所有目录
    public static File[] listDirectories(String path) {
        return new File(path).listFiles(File::isDirectory);
    }

    // 在目录内列出所有文件
    public static File[] listFilesInDirectory(final File folder) {
        return folder.listFiles(File::isFile);
    }

    // 在目录内有序列出所有文件
    public static List<File> listAllFiles(String path) {
        var all = new ArrayList<File>();
        var list = new File(path).listFiles();

        if (list != null) {
            for (var f : list) {
                if (f.isDirectory()) {
                    all.addAll(listAllFiles(f.getAbsolutePath()));
                } else {
                    all.add(f.getAbsoluteFile());
                }
            }
        }
        return all;
    }

    public static List<String> readLines(String fileName) throws IOException {
        return Files.readAllLines(new File(fileName).toPath());
    }

    public static void zipFile(String srcFileName, String zipFileName) throws IOException {
        var srcFile = new File(srcFileName);
        try (
                var fileOut = new FileOutputStream(zipFileName);
                var zipOut = new ZipOutputStream(fileOut);
                var fileIn = new FileInputStream(srcFile);
        ) {
            var zipEntry = new ZipEntry(srcFile.getName());
            zipOut.putNextEntry(zipEntry);
            final var bytes = new byte[1024];
            int length;
            while ((length = fileIn.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }

    public static void zipFiles(String[] srcFilenames, String zipFilename) throws IOException {
        try (
                var fileOut = new FileOutputStream(zipFilename);
                var zipOut = new ZipOutputStream(fileOut);
        ) {
            for (var i = 0; i < srcFilenames.length; i++) {
                var srcFile = new File(srcFilenames[i]);
                try (var fileIn = new FileInputStream(srcFile)) {
                    var zipEntry = new ZipEntry(srcFile.getName());
                    zipOut.putNextEntry(zipEntry);
                    final var bytes = new byte[1024];
                    int length;
                    while ((length = fileIn.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
            }
        }
    }

    public static void zipDirectory(String srcDirectoryName, String zipFileName) throws IOException {
        var srcDirectory = new File(srcDirectoryName);
        try (
                var fileOut = new FileOutputStream(zipFileName);
                var zipOut = new ZipOutputStream(fileOut)
        ) {
            zipFile(srcDirectory, srcDirectory.getName(), zipOut);
        }
    }

    public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut)
            throws IOException {
        if (fileToZip.isHidden()) { // Ignore hidden files as standard
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName)); // To be zipped next
                zipOut.closeEntry();
            } else {
                // Add the "/" mark explicitly to preserve structure while unzipping action is performed
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            var children = fileToZip.listFiles();
            for (var childFile : children) { // Recursively apply function to all children
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        try (
                var fis = new FileInputStream(fileToZip) // Start zipping once we know it is a file
        ) {
            var zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            var bytes = new byte[1024];
            var length = 0;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }

}
