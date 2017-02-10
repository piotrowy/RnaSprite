package pl.poznan.put.structure;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AtomsPair implements Pair<String, String> {

    private String first;
    private String second;
}
