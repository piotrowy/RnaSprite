package pl.poznan.put.core.structure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;

@Data
@Named
@Scope("prototype")
public class PdbStructureChains {

    @Getter
    @Setter
    private List<String> list;

    public final List<String> loadFromStructure(final PdbStructure structure) {
        this.list = new ArrayList<>();
        if (!structure.getModels().isEmpty()) {
            structure.getModels().stream()
                    .forEach(model -> model.getChains().stream()
                            .filter(chain -> !this.list.contains(chain.toString())).forEach(ch -> this.list.add(ch.toString())));
        }
        return this.list;
    }
}
