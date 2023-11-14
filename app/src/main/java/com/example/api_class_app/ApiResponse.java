package com.example.api_class_app;

import java.util.List;

public class ApiResponse {
    private int count;
    private String next;
    private String previous;
    private List<Results> results; // Asegúrate de que este nombre coincida con la clave en el JSON.

    // getters y setters...

    public List<Results> getResults() {
        return results;
    }

    // ...otros getters y setters si son necesarios
}