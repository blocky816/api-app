package com.tutorial.crud.dto;

public class EvaluacionRequest {
    private int deportista_id;
    private int deporte_id;

    public EvaluacionRequest() {}

    public EvaluacionRequest(int deportista_id, int deporte_id) {
        this.deportista_id = deportista_id;
        this.deporte_id = deporte_id;
    }

    // Getters y setters
    public int getDeportista_id() { return deportista_id; }
    public void setDeportista_id(int deportista_id) { this.deportista_id = deportista_id; }
    public int getDeporte_id() { return deporte_id; }
    public void setDeporte_id(int deporte_id) { this.deporte_id = deporte_id; }
}
