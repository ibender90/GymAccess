package ru.geekbrains.gym.role;

import ru.geekbrains.gym.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleType roleName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
