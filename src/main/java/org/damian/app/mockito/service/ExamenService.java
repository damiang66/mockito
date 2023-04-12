package org.damian.app.mockito.service;

import org.damian.app.mockito.entidad.Examen;

import java.util.Optional;

public interface ExamenService {
    Optional<Examen> findExamenPorNombre(String nombre);
    Examen findExamenPorNombrePorPregunta(String nombre);
    Examen guardar(Examen examen);
}
