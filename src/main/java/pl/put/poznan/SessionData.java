package pl.put.poznan;

import java.util.Date;

/**
 * Created by piotrowy on 12.04.2016.
 */
public class SessionData {

    private StructureContainer structure;
    private Date lastUseTime;

    public SessionData (StructureContainer structure, Date lastUseTime) {
        this.structure = structure;
        this.lastUseTime = lastUseTime;
    }

    public StructureContainer getStructure() {
        return structure;
    }

    public Date getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(Date lastUseTime) {
        this.lastUseTime = lastUseTime;
    }
}
