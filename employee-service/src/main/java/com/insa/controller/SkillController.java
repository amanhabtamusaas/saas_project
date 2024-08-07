package com.insa.controller;

import com.insa.dto.request.SkillRequest;
import com.insa.dto.response.SkillResponse;
import com.insa.service.SkillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills/{tenant-id}")
@RequiredArgsConstructor
@Tag (name = "Skill")
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addSkill(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @RequestBody SkillRequest skillRequest) {

        try {
            SkillResponse skill = skillService
                    .addSkill (tenantId, employeeId, skillRequest);
            return new ResponseEntity<> (skill, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the skill: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllSkills(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
            List<SkillResponse> skills = skillService
                    .getAllSkills (tenantId,employeeId);
            return ResponseEntity.ok (skills);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the skills: " + e.getMessage());
        }
    }

    @GetMapping("get/employee-skills")
    public ResponseEntity<?> getEmployeeSkills(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("employee-id") String employeeId) {

        try {
            List<SkillResponse> skills = skillService
                    .getEmployeeSkills (tenantId,employeeId);
            return ResponseEntity.ok (skills);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the skills: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get/{skill-id}")
    public ResponseEntity<?> getSkillById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("skill-id") Long skillId) {

        try {
            SkillResponse skill = skillService
                    .getSkillById (tenantId, employeeId, skillId);
            return ResponseEntity.ok (skill);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the skill: " + e.getMessage());
        }
    }

    @PutMapping("/{employee-id}/update/{skill-id}")
    public ResponseEntity<?> updateSkill(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("skill-id") Long skillId,
            @RequestBody SkillRequest skillRequest) {

        try {
            SkillResponse skill = skillService
                    .updateSkill (tenantId, employeeId, skillId, skillRequest);
            return ResponseEntity.ok (skill);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the skill: " + e.getMessage());
        }
    }

    @DeleteMapping("/{employee-id}/delete/{skill-id}")
    public ResponseEntity<String> deleteSkill(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("skill-id") Long skillId) {

        try {
            skillService.deleteSkill (tenantId, employeeId, skillId);
            return ResponseEntity.ok ("Skill deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the skill: " + e.getMessage());
        }
    }
}
