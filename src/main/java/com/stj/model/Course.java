package com.stj.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

public abstract class Course extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String name;
    private Set<Side> sides;

    public abstract String getCourseType();

    /**
     * Used for hibernate
     */
    public void setCourseType(String courseType) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FrontNine getFrontNine() {
        for (Side side : getSides()) {
            if (side instanceof FrontNine) {
                return (FrontNine) side;
            }
        }
        return new FrontNine(this);
    }

    public BackNine getBackNine() {
        for (Side side : getSides()) {
            if (side instanceof BackNine) {
                return (BackNine) side;
            }
        }
        return new BackNine(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(-1718051245, -1659252159).append(this.getCourseType()).toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Course)) {
            return false;
        }
        Course rhs = (Course) object;
        return new EqualsBuilder().append(this.getCourseType(), rhs.getCourseType()).isEquals();
    }

    private Set<Side> getSides() {
        if (sides == null) {
            sides = new HashSet<Side>();
        }
        return sides;
    }

    @SuppressWarnings("unused")
    /**
     * Only used for hibernate
     */
    private void setSides(Set<Side> sides) {
        this.sides = sides;
    }

}
