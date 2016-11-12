package pl.poznan.put.Error;

import lombok.Data;

import java.io.File;

@Data
public class ErrorData {

    private String message;

    private File picture;
}
