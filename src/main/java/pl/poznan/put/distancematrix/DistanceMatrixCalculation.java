package pl.poznan.put.distancematrix;

import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.rnamatrix.Calculation;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.torsionanglesmatrix.AngleData;
import pl.poznan.put.torsionanglesmatrix.ResidueInfo;

import java.text.DecimalFormat;

public class DistanceMatrixCalculation implements Calculation<ResidueInfo, String, AngleData> {

    private static final DecimalFormat DECIMAL_FORMAT_2 = new DecimalFormat("#,###,###,##0.00");

    @Override
    public Matrix<ResidueInfo, String, AngleData> calculateMatrix(final PdbModel model) {
//        model.getChains().stream().filter()
        return null;
    }
}
