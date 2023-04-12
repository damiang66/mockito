package org.damian.app.mockito.service;

import org.damian.app.mockito.entidad.Examen;
import org.damian.app.mockito.repositorio.ExamenRepositorio;
import org.damian.app.mockito.repositorio.ExamenRepositorioImpl;
import org.damian.app.mockito.repositorio.PreguntaRepositorio;
import org.damian.app.mockito.repositorio.PreguntaRepositorioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplSpyTest {
    @Spy
    ExamenRepositorioImpl repositorio;
    @InjectMocks
    //ExamenService service;
    ExamenServiceImpl service;
    @Spy
    PreguntaRepositorioImpl preguntaRepositorio;

    @Test
    @DisplayName("Spy con anotaciones")
    void testSpyAnotaciones() {

        List<String>preguntas = Arrays.asList("aritmetica");
        doReturn(preguntas).when(preguntaRepositorio).findPreguntasPorExamenId(anyLong());
        // when(preguntaRepositorio.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombrePorPregunta("Matematicas");
        assertEquals(5L, examen.getId());
        assertEquals("Matematicas", examen.getNombre());
        assertEquals(1, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmetica"));
        verify(repositorio).findAll();
        verify(preguntaRepositorio).findPreguntasPorExamenId(anyLong());
    }
}
