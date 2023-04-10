package org.damian.app.mockito.service;

import org.damian.app.mockito.entidad.Examen;
import org.damian.app.mockito.repositorio.ExamenRepositorio;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService{
    private ExamenRepositorio examenRepositorio;

    public ExamenServiceImpl(ExamenRepositorio examenRepositorio) {
        this.examenRepositorio = examenRepositorio;
    }

    @Override
    public  Optional<Examen> findExamenPorNombre(String nombre) {
        Optional<Examen> r =examenRepositorio.findAll()
                .stream()
                .filter(e-> e.getNombre().equalsIgnoreCase(nombre))
        .findFirst();


        return r;
    }

}
