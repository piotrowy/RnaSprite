package pl.put.poznan;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

/**
 * Created by piotrowy on 12.04.2016.
 */
@AllArgsConstructor
public class SessionData {

    /**
     * structure in which user stores pdb file.
     */
    @Getter
    @NonNull
    private final StructureContainer structure;

    /**
     * timestamp when @structure was recently called.
     */
    @Getter @Setter
    @NonNull
    private Date lastUseTime;
}
