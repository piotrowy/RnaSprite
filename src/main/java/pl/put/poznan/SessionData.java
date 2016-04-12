package pl.put.poznan;

import java.util.Date;

/**
 * Created by piotrowy on 12.04.2016.
 */
public class SessionData {

    private StructureContainer structure;
    private Date lastUseTime;

    public SessionData (StructureContainer structure, Date time) {
        this.setStructure(structure);
        this.setLastUseTime(time);
    }

    public StructureContainer getStructure() {
        return structure;
    }

    public void setStructure(StructureContainer structure) {
        this.structure = structure;
    }

    public Date getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(Date lastUseTime) {
        this.lastUseTime = lastUseTime;
    }
}
