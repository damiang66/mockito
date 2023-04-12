package org.damian.app.mockito.repositorio;

import java.util.List;

public interface PreguntaRepositorio {
    List<String> findPreguntasPorExamenId(Long id);
    void guardarVarias(List<String> preguntas);
}
