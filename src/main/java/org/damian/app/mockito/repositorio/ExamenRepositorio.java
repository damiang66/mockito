package org.damian.app.mockito.repositorio;

import org.damian.app.mockito.entidad.Examen;

import java.util.List;

public interface ExamenRepositorio {
    List<Examen> findAll();
}
