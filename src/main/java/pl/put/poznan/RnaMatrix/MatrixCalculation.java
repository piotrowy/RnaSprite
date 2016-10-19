package pl.put.poznan.RnaMatrix;

import pl.put.poznan.StructureContainer;

/**
 * Created by piotrowy on 18.10.2016.
 */
public interface MatrixCalculation {

    /**
     *
     * @return some derivative matrix.
     */
    public RnaMatrix calculateMatrix(StructureContainer structure);
}
