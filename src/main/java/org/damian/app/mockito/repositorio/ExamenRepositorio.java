package org.damian.app.mockito.repositorio;

import org.damian.app.mockito.entidad.Examen;

import java.util.List;

public interface ExamenRepositorio {
    Examen guardar(Examen examen);
    List<Examen> findAll();
}
