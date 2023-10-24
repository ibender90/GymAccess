package ru.geekbrains.gym.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.geekbrains.gym.model.Role;
import ru.geekbrains.gym.model.Token;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "gym_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;
  @Column
  private String email;
  @Column
  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "paid_period_id", referencedColumnName = "id")
  private PaidPeriod paidPeriod;

  @ManyToMany
  @Fetch(FetchMode.JOIN)
  @JoinTable(name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role singleRole:
         roles) {
      authorities.addAll(singleRole.getRoleName().getAuthorities());
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
