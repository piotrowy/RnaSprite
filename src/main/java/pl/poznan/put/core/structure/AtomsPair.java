package pl.poznan.put.core.structure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AtomsPair implements Pair<String, String> {

    private String first;
    private String second;
}
