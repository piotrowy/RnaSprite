package pl.poznan.put.repr.distancematrix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.poznan.put.core.rnamatrix.calculation.ChainCalculation;
import pl.poznan.put.core.rnamatrix.calculation.MatrixCalculation;
import pl.poznan.put.core.rnamatrix.calculation.ModelCalculation;
import pl.poznan.put.core.rnamatrix.calculation.StructureCalculation;

@Configuration
public class DistancesDataModelConfiguration {

    @Bean
    public StructureCalculation<DistanceMatrix, ResiduesAtomsSpecification> dmStructureCalculation(ModelCalculation<DistanceMatrix,
            ResiduesAtomsSpecification> modelCalculation) {
        return new StructureCalculation<>(modelCalculation);
    }

    @Bean
    public ModelCalculation<DistanceMatrix, ResiduesAtomsSpecification> dmModelCalcualtion(ChainCalculation<DistanceMatrix,
            ResiduesAtomsSpecification> chainCalculation) {
        return new ModelCalculation<>(chainCalculation);
    }

    @Bean
    public ChainCalculation<DistanceMatrix, ResiduesAtomsSpecification> dmChainCalculation(MatrixCalculation<DistanceMatrix,
            ResiduesAtomsSpecification> matrixCalculation) {
        return new ChainCalculation<>(matrixCalculation);
    }
}
