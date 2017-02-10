package pl.poznan.put.structure;

import lombok.Data;
import pl.poznan.put.pdb.PdbParsingException;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.structure.tertiary.StructureManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Data
public class PdbStructure {

    private String name;
    private List<PdbModel> models;

    public static PdbStructure fromString(final String pdbId) throws IOException, PdbParsingException {
        return new PdbStructure(pdbId);
    }

    private PdbStructure(final String pdbId) throws IOException, PdbParsingException {
        this.name = pdbId;
        this.models = StructureManager.loadStructure(pdbId);
    }

    public PdbStructure(final File file) throws IOException, PdbParsingException {
        this.name = file.getName();
        this.models = StructureManager.loadStructure(file);
    }
}
