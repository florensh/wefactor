package de.hhn.labswps.wefactor.domain;

import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public abstract class RatableEntry extends Entry {

    private Set<EntryRating> ratings;

    @OneToMany(mappedBy = "entry")
    public Set<EntryRating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<EntryRating> ratings) {
        this.ratings = ratings;
    }

    public void addRating(EntryRating rating) {
        this.ratings.add(rating);
        rating.setEntry(this);
    }

    @Transient
    @JsonProperty("rankingValue")
    private Double getRankingValue() {
        return 4.2d;
    }

}
