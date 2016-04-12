package pl.put.poznan;

import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by piotrowy on 05.04.2016.
 */
public class MatrixCsv {

    public static File DistanceMatrixToCsv(DistanceMatrix matrix) throws IOException {
        File csvFile = File.createTempFile("distanceMatrix", ".csv");
        CsvWriter csvOutput = new CsvWriter(new FileWriter(csvFile, true), ',');
        List<String> aList = new AtomNamesList().getList();

        for (int i = 0; i < 3; i++) {csvOutput.write("");}
        aList.forEach(a -> {
            try {
                csvOutput.write(a.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvOutput.endRecord();

        csvOutput.close();
        return csvFile;
    }

    public static File TorsionAnglesMatrixToCsv(TorsionAngleMatrix matrix) throws IOException {
        File csvFile = File.createTempFile("torsionAngleMatrix", ".csv");
        CsvWriter csvOutput = new CsvWriter(new FileWriter(csvFile, true), ',');

        csvOutput.close();
        return csvFile;
    }
}
