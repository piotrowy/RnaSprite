package pl.poznan.put.core.csv;

import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.poznan.put.core.mail.EmailTemplate;
import pl.poznan.put.core.mail.SendMailManager;
import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.rnamatrix.model.MtxStructure;
import pl.poznan.put.util.FileUtils;
import pl.poznan.put.util.ZipUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CsvMatrix {

    private static final char DEFAULT_SEPARATOR = '\t';

    private MtxStructure<?, ?> mtxStructure;
    private UUID sessionId;

    public void writeMatrixToCsv(String directory, String name, Matrix matrix) throws IOException {
        File csvFile = new File(directory, name);
        CSVWriter writer = new CSVWriter(new FileWriter(csvFile), DEFAULT_SEPARATOR);

        List<String> list = new ArrayList<>();
        list.add(",");
        ((List<?>) matrix.getXLabels()).forEach(label -> list.add(label.toString()));
        writer.writeNext(list.toArray(new String[list.size()]));

        IntStream.range(0, matrix.getYLabels().size())
                .forEach(index -> {
                    List<String> dataList = new ArrayList<>();
                    dataList.add(matrix.getYLabels().get(index).toString());
                    ((List<?>) matrix.getData()
                            .get(index))
                            .forEach(elem -> dataList.add(elem.toString()));
                    writer.writeNext(list.toArray(new String[list.size()]));
                });
        writer.close();
    }

    public boolean writeStructureToCsv() {
        return mtxStructure.getMtxModels()
                .stream()
                .allMatch(mtxModel -> mtxModel.getMtxChains()
                        .stream()
                        .allMatch(mtxChain -> {
                            String chainDir = "/" + sessionId.toString() + "/" + mtxStructure.getName() + "-" + mtxModel.getNumber()
                                    + "/" + mtxChain.getId();
                            boolean result = false;
                            if (new File(chainDir).mkdirs()) {
                                result = true;
                                for (int i = 0; i < mtxChain.getMatrices().size(); i++) {
                                    try {
                                        writeMatrixToCsv(chainDir, String.valueOf(i), mtxChain.getMatrices().get(i));
                                    } catch (IOException ex) {
                                        log.error("Couldn't write every matrix to csv format. Session: {}. {}", sessionId, ex);
                                        result = false;
                                    }
                                }
                            }
                            return result;
                        }));
    }

    public File zipIt() throws IOException {
        writeStructureToCsv();
        return ZipUtils.zipDirectory(sessionId.toString());
    }

    public void sendInMail(SendMailManager sendMailManager, String receiver, String subject) throws IOException {
        if (writeStructureToCsv()) {
            File zippedDir = ZipUtils.zipDirectory(sessionId.toString());
            sendMailManager.sendMail(receiver, subject, EmailTemplate.WITH_ATTACHMENTS, zippedDir);
            FileUtils.delete(zippedDir);
            log.debug("Sent email with matrices.");
        } else {
            sendMailManager.sendMail(receiver, subject, EmailTemplate.ERROR_TEMPLATE, Collections.emptyList());
            log.debug("Sent notification about error.");
        }
        FileUtils.deleteStructureFolder(sessionId);
    }
}
