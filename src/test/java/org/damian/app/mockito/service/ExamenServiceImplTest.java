package org.damian.app.mockito.service;

import org.damian.app.mockito.entidad.Examen;
import org.damian.app.mockito.repositorio.ExamenRepositorio;
import org.damian.app.mockito.repositorio.ExamenRepositorioImpl;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ExamenServiceImplTest {
    @Test
    void findExamenPorNombre() {
        ExamenRepositorio repositorio = mock(ExamenRepositorio.class);
        ExamenService service = new ExamenServiceImpl(repositorio);
        List<Examen>datos= Arrays.asList(new Examen(5L,"Matematicas"), new Examen(4L,"Lengua"),
                new Examen(2L,"Historia"));
        when(repositorio.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matematicas", examen.orElseThrow().getNombre());
    }
    @Test
    void findExamenPorNombreListaVacia() {
        ExamenRepositorio repositorio = mock(ExamenRepositorio.class);
        ExamenService service = new ExamenServiceImpl(repositorio);
        List<Examen>datos= Collections.emptyList();
        when(repositorio.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

        assertFalse(examen.isPresent());

    }
}
