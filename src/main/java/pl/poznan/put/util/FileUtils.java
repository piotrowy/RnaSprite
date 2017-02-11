package pl.poznan.put.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class FileUtils {

    public static boolean deleteStructureFolder(UUID sessionId) {
        File directory = new File(sessionId.toString());
        boolean result = false;
        if (directory.exists()) {
            result = delete(directory);
            log.debug("Successfully deleted directory {}.", sessionId);
        }
        log.debug("Directory {} does not exist.", sessionId);
        return result;
    }

    public static boolean delete(File file) {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                return file.delete();
            } else {
                Stream.of(file.list()).forEach(innerFile -> {
                    File fileToDelete = new File(file, innerFile);
                    delete(fileToDelete);
                });
                return file.delete();
            }
        } else {
            return file.delete();
        }
    }
}
