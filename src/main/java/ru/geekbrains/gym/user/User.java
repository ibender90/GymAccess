package ru.geekbrains.gym.user;

import ru.geekbrains.gym.role.UserRole;
import ru.geekbrains.gym.token.Token;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  private String firstname;
  private String lastname;
  private String email;
  private String password;

  @OneToMany(mappedBy = "user")
  @Fetch(FetchMode.JOIN)
  private List<UserRole> roles;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (UserRole singleRole:
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
