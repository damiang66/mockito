package org.damian.app.mockito.repositorio;

import org.damian.app.mockito.entidad.Examen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExamenRepositorioImpl implements ExamenRepositorio{
    @Override
    public List<Examen> findAll() {
        return Collections.emptyList(); /*Arrays.asList(new Examen(5L,"Matematicas"), new Examen(4L,"Lengua"),
                new Examen(2L,"Historia"));*/
    }
}
