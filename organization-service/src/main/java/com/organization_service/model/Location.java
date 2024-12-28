package com.organization_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Location name cannot be blank")
    @Column(name = "location_name", nullable = false,unique = true)
    private String locationName;

    @ManyToOne
    @JoinColumn(name = "parent_location_id")
    private Location parentLocation;

    @OneToMany(mappedBy = "parentLocation")
    private Set<Location> subLocations = new HashSet<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @ManyToOne
    @JoinColumn(name = "location_type_id", nullable = false)
    private LocationType locationType;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public void setParentLocation(Location parentLocation) {
        if (this.parentLocation != null) {
            this.parentLocation.getSubLocations().remove(this);
        }
        this.parentLocation = parentLocation;
        if (parentLocation != null) {
            parentLocation.getSubLocations().add(this);
        }
    }

    // Adds a sub-location and sets the parent location
    public void addSubLocation(Location subLocation) {
        subLocations.add(subLocation);
        subLocation.setParentLocation(this);
    }

    // Removes a sub-location and clears its parent location reference
    public void removeSubLocation(Location subLocation) {
        subLocations.remove(subLocation);
        subLocation.setParentLocation(null);
    }

    // Retrieves all parent locations recursively
    public List<Location> getAllParentLocations() {
        List<Location> parentLocations = new ArrayList<>();
        Location currentLocation = this;
        while (currentLocation.getParentLocation() != null) {
            currentLocation = currentLocation.getParentLocation();
            parentLocations.add(currentLocation);
        }
        return parentLocations;
    }

    // Retrieves all child locations recursively
    public List<Location> getAllChildLocations() {
        List<Location> childLocations = new ArrayList<>(subLocations);
        for (Location subLocation : subLocations) {
            childLocations.addAll(subLocation.getAllChildLocations());
        }
        return childLocations;
    }

}
