package org.damian.app.mockito.repositorio;

import java.util.List;

public class PreguntaRepositorioImpl implements PreguntaRepositorio{
    @Override
    public List<String> findPreguntasPorExamenId(Long id) {
        System.out.println("PreguntaRepositorioImpl.findPreguntasPorExamenId");
        return Datos.PREGUNTAS;
    }

    @Override
    public void guardarVarias(List<String> preguntas) {
        System.out.println("PreguntaRepositorioImpl.guardarVarias");
    }
}
