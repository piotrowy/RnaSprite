package pl.poznan.put.rnamatrix.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class Matrix<T, U, V> {

    /**
     * <p>Labels for y values. List of string or custom objects.</p>
     * <p>
     * _____________ _____________ _____________ ___
     * |_____________|_____________|_____________|___
     * |             |             |             |
     * | y.a label 1 | y.b label 1 | y.c label 1 |
     * |_____________|_____________|_____________|___
     * |             |             |             |
     * | y.a label 2 | y.b label 2 | y.c label 2 |
     * |_____________|_____________|_____________|___
     */
    private List<T> yLabels;

    /**
     * <p>Labels for x values. List of string or custom objects.</p>
     * <p>
     * ____ _____________ _____________ _____________
     * |    |             |             |             |
     * |    | x.a label 1 | x.a label 2 | x.a label 3 |
     * |____|_____________|_____________|_____________|
     * |    |             |             |             |
     * |    | x.b label 1 | x.b label 2 | x.b label 3 |
     * |____|_____________|_____________|_____________|
     * |    |             |             |             |
     * |    | x.c label 1 | x.c label 2 | x.c label 3 |
     * |____|_____________|_____________|_____________|
     * |    |             |             |             |
     */
    private List<U> xLabels;

    /**
     * <p>Data which fill matrix. </p>
     * <p>
     * <p>
     * _|_____|_____|_____|_____|
     * |     |     |     |     |
     * | 0.0 | 0.0 | 0.0 | 0.0 |
     * _|_____|_____|_____|_____|
     * |     |     |     |     |
     * | 0.0 | 0.0 | 0.0 | 0.0 |
     * _|_____|_____|_____|_____|
     */
    private List<List<V>> data;
}
