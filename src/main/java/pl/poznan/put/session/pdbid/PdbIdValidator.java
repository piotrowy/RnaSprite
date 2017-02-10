package pl.poznan.put.session.pdbid;

import lombok.RequiredArgsConstructor;
import pl.poznan.put.util.Validator;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PdbIdValidator implements Validator<String, InvalidPdbIdException> {

    private static final int PDB_ID_LENGTH = 4;

    private final PdbIdsManager pdbIdsManager;

    @Override
    public boolean validate(String pdbId) throws InvalidPdbIdException {
        if (pdbId.length() == PDB_ID_LENGTH && pdbIdsManager.pdbIdExists(pdbId)) {
            return true;
        }
        throw new InvalidPdbIdException(pdbId);
    }
}
