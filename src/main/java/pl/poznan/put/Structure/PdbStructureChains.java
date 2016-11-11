package pl.poznan.put.Structure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import pl.poznan.put.Structure.PdbStructure;

import java.util.ArrayList;
import java.util.List;

@Scope("prototype")
public class PdbStructureChains {

    @Getter
    @Setter
    private List<String> list;

    public PdbStructureChains(final PdbStructure structure) {
        this.list = new ArrayList<>();
        if (!structure.getModels().isEmpty()) {
            structure.getModels().stream()
                    .forEach(model -> model.getChains().stream()
                            .filter(chain -> !this.list.contains(chain.toString())).forEach(ch -> this.list.add(ch.toString())));
        }
    }
}
