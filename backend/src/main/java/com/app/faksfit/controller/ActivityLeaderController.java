package com.app.faksfit.controller;



import com.app.faksfit.dto.ActivityLeaderDashboardDTO;
import com.app.faksfit.dto.NoviTerminDTO;
import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.mapper.ActivityLeaderDashboardMapper;
import com.app.faksfit.mapper.TermMapper;
import com.app.faksfit.model.ActivityLeader;
import com.app.faksfit.model.Student;
import com.app.faksfit.model.Term;
import com.app.faksfit.model.User;
import com.app.faksfit.service.impl.ActivityLeaderServiceImpl;
import com.app.faksfit.service.impl.TermService;
import com.app.faksfit.service.impl.UserServiceImpl;
import com.app.faksfit.utils.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voditelj")
public class ActivityLeaderController {

    private final UserServiceImpl userService;
    private final ActivityLeaderServiceImpl activityLeaderService;
    private final ActivityLeaderDashboardMapper activityLeaderDashboardMapper;
    private final JWTUtil jwtUtil;
    private final TermService termService;
    private final TermMapper termMapper;

    public ActivityLeaderController(UserServiceImpl userService, ActivityLeaderServiceImpl activityLeaderService, ActivityLeaderDashboardMapper activityLeaderDashboardMapper, JWTUtil jwtUtil, TermService termService, TermMapper termMapper) {
        this.userService = userService;
        this.activityLeaderService = activityLeaderService;
        this.activityLeaderDashboardMapper = activityLeaderDashboardMapper;
        this.jwtUtil = jwtUtil;
        this.termService = termService;
        this.termMapper = termMapper;
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
    public ResponseEntity<String> addTerm(@RequestBody NoviTerminDTO noviTerminDTO, @RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(jwt);

        User user = userService.findByEmail(email);
        ActivityLeader activityLeader = activityLeaderService.getById(user.getUserId());

        if (activityLeader == null) {
            return ResponseEntity.status(404).body(null);
        }

        try {
            termService.addTerm(termMapper.toTermDTO(noviTerminDTO, activityLeader), activityLeader);
            return new ResponseEntity<>("Term added successfully", HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/termin/{termId}")
    public ResponseEntity<String> deleteTerm(@PathVariable Long termId, @RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(jwt);

        User user = userService.findByEmail(email);
        ActivityLeader activityLeader = activityLeaderService.getById(user.getUserId());

        if (activityLeader == null) {
            return ResponseEntity.status(404).body("Activity leader not found");
        }

        try {
            activityLeaderService.removeTerm(termId, activityLeader);
            return new ResponseEntity<>("Term deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the term", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/termin/{termId}/update")
    public ResponseEntity<String> updateTerm(
            @PathVariable Long termId,
            @RequestBody TermDTO termDTO,
            @RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(jwt);

        User user = userService.findByEmail(email);
        ActivityLeader activityLeader = activityLeaderService.getById(user.getUserId());

        if (activityLeader == null) {
            return ResponseEntity.status(404).body("Activity leader not found");
        }

        try {
            activityLeaderService.updateTerm(termId, termDTO, activityLeader);
            return new ResponseEntity<>("Term updated successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the term", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/svi-termini")
    public ResponseEntity<List<TermDTO>> getTerms(@AuthenticationPrincipal OAuth2User oauthUser) {
        if (oauthUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = oauthUser.getAttribute("email");
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Term> availableTerms = termService.getTermsByActivityLeaderUserId(user.getUserId());

        if (availableTerms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<TermDTO> termDTOList = termMapper.toTermDTOList(availableTerms);
        return ResponseEntity.ok(termDTOList);
    }

}
