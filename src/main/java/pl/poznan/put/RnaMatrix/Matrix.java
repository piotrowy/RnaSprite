package pl.poznan.put.rnamatrix;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Scope("prototype")
@Named
public class Matrix<T, U, V> {

    private String name;

    /**
     * labels for y values. List of string or custom objects.
     * <p>
     *  _____________ _____________ _____________ ___
     * |_____________|_____________|_____________|___
     * |             |             |             |
     * | y.a label 1 | y.b label 1 | y.c label 1 |
     * |_____________|_____________|_____________|___
     * |             |             |             |
     * | y.a label 2 | y.b label 2 | y.c label 2 |
     * |_____________|_____________|_____________|___
     *
     */
    private List<T> yLabels;

    /**
     * labels for x values. List of string or custom objects.
     * <p>
     *  ____ _____________ _____________ _____________
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
     *
     */
    private List<U> xLabels;

    /**
     * data which fill matrix.
     * <p>
     *
     * _|_____|_____|_____|_____|
     *  |     |     |     |     |
     *  | 0.0 | 0.0 | 0.0 | 0.0 |
     * _|_____|_____|_____|_____|
     *  |     |     |     |     |
     *  | 0.0 | 0.0 | 0.0 | 0.0 |
     * _|_____|_____|_____|_____|
     *
     */
    private List<List<V>> data;

    public Matrix() {
        this.yLabels = new ArrayList<>();
        this.xLabels = new ArrayList<>();
        this.data = new ArrayList<>();
        this.name = "";
    }
}
