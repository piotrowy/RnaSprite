package pl.poznan.put.Session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import pl.poznan.put.Structure.PdbStructure;

import java.util.Date;

@Data
@AllArgsConstructor
public class SessionData {

    /**
     * structure in which user stores pdb file.
     */
    @NonNull
    private final PdbStructure structure;

    /**
     * timestamp when @structure was recently called.
     */
    @NonNull
    private Date lastUseTime;
}
