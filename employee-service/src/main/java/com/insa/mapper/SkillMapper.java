package com.insa.mapper;

import com.insa.dto.request.SkillRequest;
import com.insa.dto.response.SkillResponse;
import com.insa.entity.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkillMapper {

    public Skill mapToEntity(SkillRequest request) {
        Skill skill = new Skill ();
        skill.setSkillType (request.getSkillType ());
        skill.setSkillLevel (request.getSkillLevel ());
        skill.setDescription (request.getDescription ());

        return skill;
    }

    public SkillResponse mapToDto(Skill skill) {
        SkillResponse response = new SkillResponse ();
        response.setId (skill.getId ());
        response.setTenantId (skill.getTenantId ());
        response.setSkillType (skill.getSkillType ());
        response.setSkillLevel (skill.getSkillLevel ());
        response.setDescription (skill.getDescription ());
        response.setCreatedAt (skill.getCreatedAt ());
        response.setUpdatedAt (skill.getUpdatedAt ());
        response.setCreatedBy (skill.getCreatedBy ());
        response.setUpdatedBy (skill.getUpdatedBy ());
        response.setEmployeeId (skill.getEmployee ().getId ());

        return response;
    }

    public Skill updateSkill(Skill skill, SkillRequest request) {

        if (request.getSkillType () != null)
            skill.setSkillType (request.getSkillType ());
        if (request.getSkillLevel () != null)
            skill.setSkillLevel (request.getSkillLevel ());
        if (request.getDescription () != null)
            skill.setDescription (request.getDescription ());

        return skill;
    }
}
