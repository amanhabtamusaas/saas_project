package com.employee_service.enums;

import lombok.Getter;

@Getter
public enum RelationshipType {
    PARENT("Parent"),
    CHILD("Child"),
    SIBLING("Sibling"),
    GRANDPARENT("Grand Parent"),
    GRANDCHILD("Grand Child"),
    AUNT("Aunt"),
    UNCLE("Uncle"),
    NIECE("Niece"),
    NEPHEW("Nephew"),
    COUSIN("Cousin"),
    SPOUSE("Spouse"),
    PARTNER("Partner"),
    IN_LAW("In Law"),
    STEP_PARENT("Step Parent"),
    STEP_CHILD("Step Child"),
    STEP_SIBLING("Step Sibling"),
    GUARDIAN("Guardian"),
    FOSTER_PARENT("Foster Parent"),
    FOSTER_CHILD("Foster Child"),
    ADOPTIVE_PARENT("Adoptive Child"),
    ADOPTED_CHILD("Adopted Child");

    private final String relationshipType;

    RelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }
}
