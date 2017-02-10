package pl.poznan.put.repr.torsionanglesmatrix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.poznan.put.core.rnamatrix.calculation.ChainCalculation;
import pl.poznan.put.core.rnamatrix.calculation.MatrixCalculation;
import pl.poznan.put.core.rnamatrix.calculation.ModelCalculation;
import pl.poznan.put.core.rnamatrix.calculation.StructureCalculation;

import java.util.Set;

@Configuration
public class TorsionAnglesDataModelConfiguration {

    @Bean
    public StructureCalculation<TorsionAnglesMatrix, Set<String>> tmStructureCalculation(ModelCalculation<TorsionAnglesMatrix,
            Set<String>> modelCalculation) {
        return new StructureCalculation<>(modelCalculation);
    }

    @Bean
    public ModelCalculation<TorsionAnglesMatrix, Set<String>> tmModelCalcualtion(ChainCalculation<TorsionAnglesMatrix,
            Set<String>> chainCalculation) {
        return new ModelCalculation<>(chainCalculation);
    }

    @Bean
    public ChainCalculation<TorsionAnglesMatrix, Set<String>> tmChainCalculation(MatrixCalculation<TorsionAnglesMatrix,
            Set<String>> matrixCalculation) {
        return new ChainCalculation<>(matrixCalculation);
    }
}
