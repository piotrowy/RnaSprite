package pl.poznan.put.Structure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Scope("prototype")
@Component
@Data
public class PdbStructureChains {

    @Getter
    @Setter
    private List<String> list;

    public List<String> loadFromStructure(final PdbStructure structure) {
        this.list = new ArrayList<>();
        if (!structure.getModels().isEmpty()) {
            structure.getModels().stream()
                    .forEach(model -> model.getChains().stream()
                            .filter(chain -> !this.list.contains(chain.toString())).forEach(ch -> this.list.add(ch.toString())));
        }
        return this.list;
    }
}
