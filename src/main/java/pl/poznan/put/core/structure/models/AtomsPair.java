package pl.poznan.put.core.structure.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.poznan.put.util.Pair;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AtomsPair implements Pair<String, String> {

    private String first;
    private String second;
}
