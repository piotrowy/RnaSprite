package pl.poznan.put.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@UtilityClass
public class ZipUtils {

    public static File zipDirectory(String directory) throws IOException {
        return zipIt(directory, findFilesToZip(new File(directory), directory, new ArrayList<>()));
    }

    public static void unZipTask(String file) {
        //// TODO: 26.01.2017
    }

    private static File zipIt(String directory, List<String> files) throws IOException {
        byte[] buffer = new byte[1024];
        String zipFile = directory + ".zip";


        FileInputStream in = null;
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            log.debug("Zipping directory {}.", directory);

            for (String file : files) {
                log.debug("Adding file {}", file);
                ZipEntry ze = new ZipEntry(file);
                zos.putNextEntry(ze);

                in = new FileInputStream(directory + File.separator + file);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
            }

            zos.closeEntry();
            zos.close();
            log.debug("Zipping ended");
        } catch (IOException e) {
            log.error("Zipping failed. {}", e);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return new File(zipFile);
    }

    private static List<String> findFilesToZip(File node, String directory, List<String> fileList) {
        if (node.isFile()) {
            fileList.add(createZipEntry(node.getAbsoluteFile().toString(), directory));
        }

        if (node.isDirectory()) {
            Stream.of(node.list()).forEach(fileName -> findFilesToZip(new File(node, fileName), directory, fileList));

        }

        return fileList;
    }

    private static String createZipEntry(String file, String directory) {
        return file.substring(directory.length() + 1, file.length());
    }
}
