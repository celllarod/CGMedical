package com.tfg.apirest.service;


import com.tfg.apirest.dto.DatosCalculoDosisDTO;
import com.tfg.apirest.view.CargaView;
import com.tfg.apirest.view.PropiedadSimpleView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

import com.tfg.apirest.dto.DatosCalculoMezclasDTO;

@Service
@RequiredArgsConstructor
@Validated
public class CalculadoraService {
    /** Constantes */
    private static final String SF = "Suero Fisiológico";

    /**
     * Permite calcular cuál debe ser la mezcla que se debe añadir a la bomba de infusión
     * @return usuario
     */
    // TODO: validaciones de negocio datos
    public List<CargaView> calcularMezclas(DatosCalculoMezclasDTO datos) {

        var receta = new ArrayList<CargaView>();
        AtomicReference<Double> cargaAcumulada = new AtomicReference<>(0.0); // mL
        BinaryOperator<Double> adder = (x, y) -> Double.sum(x, y);

        var volumenBomba = datos.getVolumenBomba().getValor(); // mL
        var farmacoDominante = datos.getFarmacoDominante();
        var farmacosSecundarios = datos.getFarmacosSecundarios();

        // Unidad micras o milis. Pasar todo a milis para hacer cálculos.
        var dosisA = ("mg/día".equals(farmacoDominante.getDosis().getUnidad())) ? farmacoDominante.getDosis().getValor() :  farmacoDominante.getDosis().getValor()/1000; // mg/día
        var concentracionA = ("mg/mL".equals(farmacoDominante.getConcentracion().getUnidad())) ? farmacoDominante.getConcentracion().getValor() :  farmacoDominante.getConcentracion().getValor()/1000; // mg/mL
        var presentacionA = ("mg/mL".equals(farmacoDominante.getPresentacion().getUnidad())) ? farmacoDominante.getPresentacion().getValor() :  farmacoDominante.getPresentacion().getValor()/1000; // mg/mL

        var flujo = dosisA / concentracionA; // mL/dia
        var cantidadA = volumenBomba * concentracionA; // mg
        var cargaA = cantidadA / presentacionA; // mL
        cargaAcumulada.accumulateAndGet(cargaA, adder);

        receta.add(new CargaView(farmacoDominante.getNombre(), String.format("%.3f", cargaA) + " mL"));

        if (!Objects.isNull(farmacosSecundarios)) {
            farmacosSecundarios.forEach(b -> {
                var dosisB = ("mg/día".equals(b.getDosis().getUnidad())) ? b.getDosis().getValor() :  b.getDosis().getValor()/1000; // mg/día
                var presentacionB = ("mg/mL".equals(b.getPresentacion().getUnidad())) ? b.getPresentacion().getValor() :  b.getPresentacion().getValor()/1000; // mg/mL

                var concentracionBA = dosisB / flujo; // mg/mL
                var cantidadB =volumenBomba * concentracionBA; // mg
                var cargaB = cantidadB / presentacionB; // mL
                receta.add(new CargaView(b.getNombre(), String.format("%.3f",cargaB) + " mL"));
                cargaAcumulada.accumulateAndGet(cargaB, adder);
            });
        }

        Double cargaSF = volumenBomba - cargaAcumulada.get();
        receta.add(new CargaView(SF, String.format("%.3f", cargaSF) + " mL"));
        return receta;
    }

    public PropiedadSimpleView calcularDosis(DatosCalculoDosisDTO datos) {

        var result = new PropiedadSimpleView();

        var dosisActualFd = ("mg/día".equals(datos.getDosisActualFd().getUnidad())) ? datos.getDosisActualFd().getValor() :  datos.getDosisActualFd().getValor()/1000; // mg/día
        var dosisActualFs = ("mg/día".equals(datos.getDosisActualFs().getUnidad())) ? datos.getDosisActualFs().getValor() :  datos.getDosisActualFs().getValor()/1000; // mg/día

        Double dosisNuevaFd = null;
        Double dosisNuevaFs = null;

        if (!Objects.isNull(datos.getDosisNuevaFd())){
            dosisNuevaFd = ("mg/día".equals(datos.getDosisNuevaFd().getUnidad())) ? datos.getDosisNuevaFd().getValor() :  datos.getDosisNuevaFd().getValor()/1000; // mg/día
            result.setValor(calcularNuevaDosisFs(dosisActualFd, dosisActualFs, dosisNuevaFd));
        } else if(!Objects.isNull(datos.getDosisNuevaFs())){
            dosisNuevaFs = ("mg/día".equals(datos.getDosisNuevaFs().getUnidad())) ? datos.getDosisNuevaFs().getValor() :  datos.getDosisNuevaFs().getValor()/1000; // mg/día
            result.setValor(calcularNuevaDosisFd(dosisActualFd, dosisActualFs, dosisNuevaFs));

        }

        result.setUnidad("mg/día");
        return result;

    }

    private double calcularNuevaDosisFd(double dosisActualFd, double dosisActualFs, double dosisNuevaFs){
        return (dosisNuevaFs * dosisActualFd) / dosisActualFs;
    }

    private double calcularNuevaDosisFs(double dosisActualFd, double dosisActualFs, double dosisNuevaFd){
        return (dosisNuevaFd * dosisActualFs) / dosisActualFd;
    }

}
