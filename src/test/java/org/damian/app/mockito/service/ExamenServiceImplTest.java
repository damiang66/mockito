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

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {
    @Mock
    ExamenRepositorioImpl repositorio;
    @InjectMocks
    //ExamenService service;
    ExamenServiceImpl service;
    @Mock
    PreguntaRepositorioImpl preguntaRepositorio;
    @Captor
    ArgumentCaptor<Long> captor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //  repositorio = mock(ExamenRepositorio.class);
        //  preguntaRepositorio = mock(PreguntaRepositorio.class);
        // service = new ExamenServiceImpl(repositorio, preguntaRepositorio);
    }

    @Test
    void findExamenPorNombre() {


        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matematicas", examen.orElseThrow().getNombre());
    }

    @Test
    void findExamenPorNombreListaVacia() {

        List<Examen> datos = Collections.emptyList();
        when(repositorio.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

        assertFalse(examen.isPresent());

    }

    @Test
    void testPreguntaExamen() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        when(preguntaRepositorio.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        // Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
        Examen examen = service.findExamenPorNombrePorPregunta("Matematicas");
        assertEquals(4, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("sumar"));
    }

    @Test
    void testPreguntaExamenVerify() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        when(preguntaRepositorio.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        // Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
        Examen examen = service.findExamenPorNombrePorPregunta("Matematicas");
        assertEquals(4, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("sumar"));
        verify(repositorio).findAll();
        verify(preguntaRepositorio).findPreguntasPorExamenId(5L);
    }

    @Test
    void testGuardarExamen() {
        // given
        Examen newExamen = Datos.E;
        newExamen.setPreguntas(Datos.PREGUNTAS);
        when(repositorio.guardar(any(Examen.class))).then(new Answer<Examen>() {
            Long secuencia = 8L;

            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen = invocation.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        });
        // when
        Examen examen = service.guardar(newExamen);
        // then
        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("fisica", examen.getNombre());
        verify(repositorio).guardar(any(Examen.class));
        //  verify(preguntaRepositorio).guardarVarias(anyList());
    }

    @Test
    void testManejodeErrores() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        when(preguntaRepositorio.findPreguntasPorExamenId(anyLong())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> {
            service.findExamenPorNombrePorPregunta("Matematicas");
        });
        verify(repositorio).findAll();
        verify(preguntaRepositorio).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testArgumentMatchers() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        when(preguntaRepositorio.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombrePorPregunta("Matematicas");
        verify(repositorio).findAll();
        //verify(preguntaRepositorio).findPreguntasPorExamenId(argThat(arg-> arg !=null &&arg.equals(5L)));
        // verify(preguntaRepositorio).findPreguntasPorExamenId(eq(5L));
        verify(preguntaRepositorio).findPreguntasPorExamenId(argThat(arg -> arg != null && arg >= 5L));
    }

    @Test
    void testArgumentMatchersConMetodo() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        when(preguntaRepositorio.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombrePorPregunta("Historia");
        verify(repositorio).findAll();

        verify(preguntaRepositorio).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));
    }

    public static class MiArgsMatchers implements ArgumentMatcher<Long> {
        private Long argument;

        @Override
        public boolean matches(Long argument) {
            argument = argument;
            return argument != null && argument > 0;
        }

        @Override
        public String toString() {
            return "es para un mensaje personalizado de error que imprime mockito cuando falla el test" +
                    "debe ser un entero positivo" +
                    "el que se paso es " + argument;

        }
    }

    @Test
    void testArgumentCaptor() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        when(preguntaRepositorio.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombrePorPregunta("Matematicas");
        //ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(preguntaRepositorio).findPreguntasPorExamenId(captor.capture());
        System.out.println(captor.getValue());
        assertEquals(5L, captor.getValue());
    }

    @Test
    @Disabled
    void testDoThrow() {
        Examen examen = Datos.E;
        examen.setPreguntas(Datos.PREGUNTAS);
        doThrow(IllegalArgumentException.class).when(preguntaRepositorio).guardarVarias(anyList());
        assertThrows(IllegalArgumentException.class, () -> {
            service.guardar(examen);
        });
    }

    @Test
    void testDoAnswer() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        // when(preguntaRepositorio.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return id == 5L ? Datos.PREGUNTAS : null;
        }).when(preguntaRepositorio).findPreguntasPorExamenId(anyLong());
        Examen examen = service.findExamenPorNombrePorPregunta("Matematicas");
        assertEquals(5L, examen.getId());
        assertEquals("Matematicas", examen.getNombre());
        assertEquals(4, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("sumar"));
        verify(preguntaRepositorio).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testDoCallRealMetodo() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
       // when(preguntaRepositorio.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        doCallRealMethod().when(preguntaRepositorio).findPreguntasPorExamenId(anyLong());
        Examen examen= service.findExamenPorNombrePorPregunta("Matematicas");
        assertEquals(5L, examen.getId());
        assertEquals("Matematicas", examen.getNombre());
    }

    @Test
    void testSpy() {
    ExamenRepositorio examenRepositorio = spy(ExamenRepositorioImpl.class);
    PreguntaRepositorio preguntaRepositorio = spy(PreguntaRepositorioImpl.class);
    ExamenService examenService = new ExamenServiceImpl(examenRepositorio,preguntaRepositorio);
    List<String>preguntas = Arrays.asList("aritmetica");
    doReturn(preguntas).when(preguntaRepositorio).findPreguntasPorExamenId(anyLong());
   // when(preguntaRepositorio.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
    Examen examen = examenService.findExamenPorNombrePorPregunta("Matematicas");
    assertEquals(5L, examen.getId());
    assertEquals("Matematicas", examen.getNombre());
    assertEquals(1, examen.getPreguntas().size());
    assertTrue(examen.getPreguntas().contains("aritmetica"));
    verify(examenRepositorio).findAll();
    verify(preguntaRepositorio).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testOrden() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        service.findExamenPorNombrePorPregunta("Matematicas");
        service.findExamenPorNombrePorPregunta("Historia");
        InOrder inOrder= inOrder(preguntaRepositorio);
        inOrder.verify(preguntaRepositorio).findPreguntasPorExamenId(5L);
        inOrder.verify(preguntaRepositorio).findPreguntasPorExamenId(2L);

    }
    void testOrden2() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        service.findExamenPorNombrePorPregunta("Matematicas");
        service.findExamenPorNombrePorPregunta("Historia");
        InOrder inOrder= inOrder(repositorio,preguntaRepositorio);
        inOrder.verify(repositorio).findAll();
        inOrder.verify(preguntaRepositorio).findPreguntasPorExamenId(5L);
        inOrder.verify(repositorio).findAll();
        inOrder.verify(preguntaRepositorio).findPreguntasPorExamenId(2L);


    }

    @Test
    void testNumero() {
        when(repositorio.findAll()).thenReturn(Datos.EXAMEN);
        service.findExamenPorNombrePorPregunta("Matematicas");
        verify(preguntaRepositorio, times(1)).findPreguntasPorExamenId(5L);
        verify(preguntaRepositorio, atLeast(1)).findPreguntasPorExamenId(5L);
        verify(preguntaRepositorio, atLeastOnce()).findPreguntasPorExamenId(5L);
        verify(preguntaRepositorio, atMost(1)).findPreguntasPorExamenId(5L);
        verify(preguntaRepositorio, atMostOnce()).findPreguntasPorExamenId(5L);


    }
    @Test
    void testNumero1() {
        when(repositorio.findAll()).thenReturn(Collections.emptyList());
        service.findExamenPorNombrePorPregunta("Matematicas");
        verify(preguntaRepositorio, never()).findPreguntasPorExamenId(5L);
        verifyNoInteractions(preguntaRepositorio);
        verify(repositorio).findAll();



    }
}
