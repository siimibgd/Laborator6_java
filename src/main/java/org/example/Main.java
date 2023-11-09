package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Optional.*;

public class Main {
    static void scriere(List<Angajat> angajat) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        File file = new File("src/main/resources/angajati.json");
        objectMapper.writeValue(file,angajat);
    }
    public static void main(String[] args) throws IOException {
        List<Angajat> angajat = new ArrayList<>();
        angajat.add(new Angajat("Stefan","Inginer", LocalDate.of(2002,12,12),2700));
        angajat.add(new Angajat("Sfan","sef Sal", LocalDate.of(2002,2,12),2740));
        angajat.add(new Angajat("Stan","director Ingine", LocalDate.of(2022,6,22),270));
        angajat.add(new Angajat("San","Iner", LocalDate.of(2004,12,2),254));
        angajat.add(new Angajat("Ion","giner", LocalDate.of(2007,1,12),3701));
        scriere(angajat);
        /**
         * ex1
         */
        angajat.forEach(System.out::println);
        System.out.println("");
        /**
         * ex2
         */
        angajat.stream()
                .filter(angajat1 -> {
                    boolean b = angajat1.getSalariul() > 2500;
                    return b;
                })
                .forEach(System.out::println);
        /**
         * ex3
         */
        List<Angajat> colectie = angajat
                .stream()
                .filter(angajat1 -> {
                    boolean b=angajat1.getData_angajarii().getYear()==LocalDate.now().getYear()-1;
                    boolean c=angajat1.getData_angajarii().getMonth()==LocalDate.of(2000,4,1).getMonth();
                    return b&c;
                })
                .filter(angajat1 -> {
                    boolean b =angajat1.getPostul().contains("sef");
                    boolean c = angajat1.getPostul().contains("director");
                    return b|c;
                })
                .collect(Collectors.toList());
        colectie.stream()
            .forEach(System.out::println);
        System.out.println();
        /**
         * ex4
         */
        angajat.stream()
                .filter(angajat1 -> {
                    boolean b =angajat1.getPostul().contains("sef");
                    boolean c = angajat1.getPostul().contains("director");
                    return !(b|c);
                })
                .sorted(Comparator.comparing(Angajat::getSalariul).reversed())
                .forEach(System.out::println);
        System.out.println();
        /**
         * ex5
         */
        List<String> numeMajusculeAngajati = angajat.stream()
                .map(angajat1 -> angajat1.getNumele().toUpperCase())
                .collect(Collectors.toList());
        numeMajusculeAngajati.forEach(System.out::println);
        System.out.println();
        /**
         * ex6
         */
        angajat.stream()
                .map(Angajat::getSalariul)
                .filter(salariu -> salariu<3000)
                .forEach(System.out::println);
        /**
         * ex7
         */
        Optional<Angajat> primulAngajat =
                angajat.stream()
                        .min(Comparator.comparing(Angajat::getData_angajarii));
        primulAngajat.ifPresent(
                angajat1 -> System.out.println(angajat1)
        );
        /**
         * ex8
         */
        DoubleSummaryStatistics summaryStatistics = angajat.stream()
                .collect(Collectors.summarizingDouble(Angajat::getSalariul));
        System.out.println();
        System.out.println("Salariul mediu:"+summaryStatistics.getAverage());
        System.out.println("Salariul minim:"+summaryStatistics.getMin());
        System.out.println("Salariul maxim:"+summaryStatistics.getMax());

        /**
         * ex9
         */
        Optional<Angajat> angajatt= angajat.stream()
                        .filter(angajat1 -> angajat1.getNumele().equalsIgnoreCase("ion"))
                        .findAny();
        angajatt.ifPresentOrElse(angajat1 -> System.out.println("Firma are cel puțin un Ion angajat"),
                () ->  System.out.println("Firma nu are nici un Ion angajat")
                        );
        /**
         * ex10
         */
        long nrAngajati =
                angajat.stream()
                        .filter(angajat1 ->  angajat1.getData_angajarii().getYear()==LocalDate.now().getYear()-1 &&
                                (angajat1.getData_angajarii().getMonth()== Month.AUGUST ||
                                 angajat1.getData_angajarii().getMonth()==Month.JULY ||
                                 angajat1.getData_angajarii().getMonth()==Month.JUNE))
                        .count();
        System.out.println(nrAngajati);
    }
}