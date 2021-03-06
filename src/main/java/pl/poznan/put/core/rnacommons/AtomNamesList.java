package pl.poznan.put.core.rnacommons;

import lombok.Getter;
import lombok.Setter;
import pl.poznan.put.atom.AtomName;
import pl.poznan.put.rna.Phosphate;
import pl.poznan.put.rna.Ribose;
import pl.poznan.put.rna.base.Adenine;
import pl.poznan.put.rna.base.Cytosine;
import pl.poznan.put.rna.base.Guanine;
import pl.poznan.put.rna.base.Uracil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Named;

@Named
public class AtomNamesList {

    /**
     * static list stores all atom names.
     */
    private static List<String> staticList;

    /**
     * plain list serves to serializing.
     */
    @Getter
    @Setter
    private List<String> list;

    static {
        Set<AtomName> atomNames = new LinkedHashSet<>();
        atomNames.addAll(Phosphate.getInstance().getAtoms());
        atomNames.addAll(Ribose.getInstance().getAtoms());
        atomNames.addAll(Adenine.getInstance().getAtoms());
        atomNames.addAll(Guanine.getInstance().getAtoms());
        atomNames.addAll(Cytosine.getInstance().getAtoms());
        atomNames.addAll(Uracil.getInstance().getAtoms());
        staticList = atomNames.stream().map(AtomName::getName).collect(Collectors.toList());
    }

    /**
     * it constructs object to store all atom names.
     */
    public AtomNamesList() {
        this.list = staticList;
    }
}

