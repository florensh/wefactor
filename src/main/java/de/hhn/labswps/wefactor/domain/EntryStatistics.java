package de.hhn.labswps.wefactor.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EntryStatistics {

    private int views = 0;

    @Column(columnDefinition = "int(5) default 0")
    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void addViews(int count) {
        this.views += count;
    }

}
