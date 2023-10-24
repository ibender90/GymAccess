package ru.geekbrains.gym.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coach")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "gym_user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "coach")
    private List<Workout> workouts;

    @OneToOne(mappedBy = "coach")
    private CoachProfile coachProfile;
}
