package pl.poznan.put.rnamatrix;

import pl.poznan.put.structure.PdbStructure;

import java.util.List;

public interface MatrixFactory<T, U, V, X> {

    List<Matrix<T, U, V>> create(PdbStructure structure, X args);
}
