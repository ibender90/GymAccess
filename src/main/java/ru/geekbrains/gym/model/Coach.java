package ru.geekbrains.gym.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.util.List;

@Data
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coach")
public class Coach extends User {

    @OneToMany(mappedBy = "coach")
    private List<Workout> workouts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coach_profile_id", referencedColumnName = "id")
    private CoachProfile coachProfile;
}
