package org.leafall.authservice.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Error {

    private List<String> errors = new ArrayList<>();
}
