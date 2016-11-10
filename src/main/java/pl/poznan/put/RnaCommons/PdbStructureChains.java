package pl.poznan.put.RnaCommons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import pl.poznan.put.StructureContainer;

import java.util.ArrayList;
import java.util.List;

@Scope("prototype")
public class PdbStructureChains {

    @Getter
    @Setter
    private List<String> list;

    public PdbStructureChains(StructureContainer structure) {
        this.list = new ArrayList<>();
        if (!structure.getStructureList().isEmpty()) {
            structure.getStructureList().stream().forEach(model -> {
                model.getChains().stream().filter(chain -> !this.list.contains(chain.toString())).forEach(ch ->
                        this.list.add(ch.toString()));
            });
        }
    }
}
