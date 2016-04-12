package pl.put.poznan;

import java.util.Date;

/**
 * Created by piotrowy on 12.04.2016.
 */
public class SessionData {

    private StructureContainer structure;
    private Date time;

    public SessionData (StructureContainer structure, Date time) {
        this.setStructure(structure);
        this.setTime(time);
    }

    public StructureContainer getStructure() {
        return structure;
    }

    public void setStructure(StructureContainer structure) {
        this.structure = structure;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
