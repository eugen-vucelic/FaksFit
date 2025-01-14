package com.app.faksfit.controller;



import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.dto.TerminDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/voditelj")
public class ActivityLeaderController {

    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentManager(){

        Map<String, Object> response = new HashMap<>();
        response.put("activityLeaderID", "WTF"); // ID voditelja
        response.put("activityType", "Nogomet"); // Aktivnost

        return ResponseEntity.ok(response);
    }

    @PostMapping("/noviTermin")
    public ResponseEntity<String> createTermin(@RequestBody TerminDTO terminDTO) {

        // Log podataka za provjeru
        System.out.println("Primljeni podaci za novi termin:");
        System.out.println("Voditelj ID: " + terminDTO.activityLeaderID());
        System.out.println("Aktivnost voditelja: " + terminDTO.activityType());
        System.out.println("Datum: " + terminDTO.date());
        System.out.println("Početak: " + terminDTO.start());
        System.out.println("Kraj: " + terminDTO.end());
        System.out.println("Lokacija: " + terminDTO.location());
        System.out.println("Bodovi: " + terminDTO.maxPoints());
        System.out.println("Maksimalni kapacitet: " + terminDTO.maxCapacity());
        System.out.println("Lista prijavljenih studenata: " + terminDTO.listOfStudentsIDs());
        //moze se i trenutni kapacitet slat ili ćemo ga racunat po duljini liste

        return ResponseEntity.ok("Novi termin uspješno kreiran.");
    }
}
