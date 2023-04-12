package org.damian.app.mockito.service;

import org.damian.app.mockito.entidad.Examen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Datos {
   public final static List<Examen> EXAMEN= Arrays.asList(new Examen(5L,"Matematicas"), new Examen(4L,"Lengua"),
            new Examen(2L,"Historia"));
   public final static List<Examen> EXAMENES_ID_NULL= Arrays.asList(new Examen(null,"Matematicas"), new Examen(null,"Lengua"),
           new Examen(null,"Historia"));
   public final static List<Examen> EXAMEN_ID_NEGATIVO= Arrays.asList(new Examen(-5L,"Matematicas"), new Examen(null,"Lengua"),
           new Examen(-2L,"Historia"));
   public final static List<String>PREGUNTAS = Arrays.asList("aritmetica", "logica", "sumar", "restar");
   public final static Examen E = new Examen(null, "fisica");
}
