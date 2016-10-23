package pl.put.poznan.RnaMatrix;

import pl.put.poznan.StructureContainer;

import java.text.DecimalFormat;
import java.util.Collections;

/**
 * Created by piotrowy on 19.10.2016.
 */
public class TorsionAnglesMatrix extends RnaMatrix {

    /**
     *
     */
    private DecimalFormat df2;

    /**
     *
     */
    public TorsionAnglesMatrix() {
        this.df2 = new DecimalFormat("#,###,###,##0.00");
        super.setCalculationMethod(new TorsionAnglesMatrixCalculation());
        super.setData(Collections.EMPTY_LIST);
        super.setXLabels(Collections.EMPTY_LIST);
        super.setYLabels(Collections.EMPTY_LIST);

    }
}
