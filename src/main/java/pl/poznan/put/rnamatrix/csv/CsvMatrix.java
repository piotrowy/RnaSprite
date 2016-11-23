package pl.poznan.put.rnamatrix.csv;

import com.opencsv.CSVWriter;
import jersey.repackaged.com.google.common.primitives.Ints;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import pl.poznan.put.rnamatrix.Matrix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Data
public class CsvMatrix {

    private static final char DEFAULT_SEPARATOR = ',';

    public static File writeMatricesToCsv(List<Matrix> matrices) {
        List<File> files = matrices.stream().map(CsvMatrix::writeMatrixToCsv).collect(Collectors.toList());
        return null;
    }

    private static File writeMatrixToCsv(Matrix matrix) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(File.createTempFile(matrix.getName(), ".csv")));

            List<List<String>> xLabels = getXLabelsValues(matrix);

            IntStream.range(0, xLabels.size()).forEach(idx -> {
                List<String> shift = getShift(matrix);
                if (shift.addAll(xLabels.get(idx))) {
                    writer.writeNext(shift.toArray(new String[shift.size()]));
                }
            });

            writer.close();

        } catch (IOException ex) {
            log.error("Zesrało się!", ex);
        }
        return null;
    }

    private static List<String> getShift(Matrix matrix) {
        return Arrays.asList(matrix.getYLabels().get(0).getClass().getDeclaredFields()).stream().map(Field::getName).collect(Collectors.toList());
    }

    private static List<List<String>> getXLabelsValues(Matrix matrix) {
        return getLabelsValues(matrix.getXLabels());
    }

    private static List<List<String>> getYLabelsValues(Matrix matrix) {
        return getLabelsValues(matrix.getYLabels());
    }

    private static List<List<String>> getLabelsValues(List<?> dataList) {
        return dataList.stream()
                .map(data -> Arrays.asList(data.getClass().getDeclaredFields()).stream()
                        .map(field -> {
                            field.setAccessible(true);
                            try {
                                return field.get(data).toString();
                            } catch (IllegalAccessException e) {
                                log.error("Reflection failed", e);
                            }
                            return null;
                        })
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
