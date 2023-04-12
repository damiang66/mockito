package org.damian.app.mockito.service;

import org.damian.app.mockito.entidad.Examen;
import org.damian.app.mockito.repositorio.ExamenRepositorio;
import org.damian.app.mockito.repositorio.PreguntaRepositorio;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService{
    private ExamenRepositorio examenRepositorio;
    private PreguntaRepositorio preguntaRepositorio;

    public ExamenServiceImpl(ExamenRepositorio examenRepositorio, PreguntaRepositorio preguntaRepositorio) {
        this.examenRepositorio = examenRepositorio;
        this.preguntaRepositorio= preguntaRepositorio;
    }

    @Override
    public  Optional<Examen> findExamenPorNombre(String nombre) {
        Optional<Examen> r =examenRepositorio.findAll()
                .stream()
                .filter(e-> e.getNombre().equalsIgnoreCase(nombre))
        .findFirst();


        return r;
    }

    @Override
    public Examen findExamenPorNombrePorPregunta(String nombre) {
        Optional<Examen> examenOptional = findExamenPorNombre(nombre);
        Examen examen = null;
        if (examenOptional.isPresent()){
             examen = examenOptional.get();
            List<String> preguntas = preguntaRepositorio.findPreguntasPorExamenId(examenOptional.get().getId());
            examen.setPreguntas(preguntas);

        }
        return examen;
    }

    @Override
    public Examen guardar(Examen examen) {
        if (examen.getPreguntas().size()==0){
            System.out.println("hola");
            preguntaRepositorio.guardarVarias(examen.getPreguntas());
        }
        return examenRepositorio.guardar(examen);
    }

}
