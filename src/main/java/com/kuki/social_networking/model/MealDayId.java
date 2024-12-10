package com.kuki.social_networking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
public class MealDayId implements Serializable {

    @Min(1)
    @Max(30)
    @Column(nullable = false)
    private int day;
    @Column(nullable = false)
    private UUID plannerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealDayId mealDayId = (MealDayId) o;
        return day == mealDayId.day && Objects.equals(plannerId, mealDayId.plannerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, plannerId);
    }

    @Override
    public String toString() {
        return "MealDayId{" +
            "day=" + day +
            ", plannerId=" + plannerId +
            '}';
    }
}