package pl.poznan.put.rnamatrix;

import pl.poznan.put.structure.PdbStructure;

import java.util.List;
import java.util.Optional;

public interface MatrixFactory<T, U, V, X> {

    List<Matrix<T, U, V>> get(PdbStructure structure, Optional<X> args);
}
