package com.app.faksfit.controller;



import com.app.faksfit.dto.ActivityLeaderDashboardDTO;
import com.app.faksfit.dto.StudentSettingsDTO;
import com.app.faksfit.dto.TerminDTO;
import com.app.faksfit.mapper.ActivityLeaderDashboardMapper;
import com.app.faksfit.model.ActivityLeader;
import com.app.faksfit.model.User;
import com.app.faksfit.service.impl.ActivityLeaderServiceImpl;
import com.app.faksfit.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voditelj")
public class ActivityLeaderController {

    private final UserServiceImpl userService;
    private final ActivityLeaderServiceImpl activityLeaderService;
    private final ActivityLeaderDashboardMapper activityLeaderDashboardMapper;

    public ActivityLeaderController(UserServiceImpl userService, ActivityLeaderServiceImpl activityLeaderservice, ActivityLeaderDashboardMapper activityLeaderDashboardMapper) {
        this.userService = userService;
        this.activityLeaderService = activityLeaderservice;
        this.activityLeaderDashboardMapper = activityLeaderDashboardMapper;
    }

    @GetMapping("/current")
    public ResponseEntity<ActivityLeaderDashboardDTO> getCurrentActivityLeader(@AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        User user = userService.findByEmail(email);
        ActivityLeader activityLeader = activityLeaderService.getById(user.getUserId());

        if (activityLeader == null) {
            return ResponseEntity.status(404).body(null);
        }

        ActivityLeaderDashboardDTO activityLeaderDashboardDTO = activityLeaderDashboardMapper.toActivityLeaderDashboardDTO(activityLeader);

        return ResponseEntity.ok(activityLeaderDashboardDTO);
    }

    @PostMapping("/noviTermin")
    public ResponseEntity<String> createTermin(@RequestBody TerminDTO terminDTO) {

        // Log podataka za provjeru
        System.out.println("Primljeni podaci za novi termin:");
//        System.out.println("Voditelj ID: " + terminDTO.activityLeaderID());
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
