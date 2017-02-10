package pl.poznan.put.torsionanglesmatrix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.poznan.put.rnamatrix.calculation.ChainCalculation;
import pl.poznan.put.rnamatrix.calculation.MatrixCalculation;
import pl.poznan.put.rnamatrix.calculation.ModelCalculation;
import pl.poznan.put.rnamatrix.calculation.StructureCalculation;

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
