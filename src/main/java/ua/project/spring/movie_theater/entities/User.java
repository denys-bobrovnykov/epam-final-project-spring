package ua.project.spring.movie_theater.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
//    private Set<Role> roles;
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name="enabled", nullable = false)
    private boolean enabled;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @OneToMany(mappedBy = "user")


    private Set<Ticket> userTickets = new HashSet<>();

    public Set<Ticket> getUserTickets() {
        return userTickets;
    }

    public void setUserTickets(Set<Ticket> userTickets) {
        this.userTickets = userTickets;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

//    public Set<Role> getRoles() {
//        return roles;
//    }

//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static UserBuilder userBuilder() {
        return new UserBuilder();
    }

    public User(){}

    private User(UserBuilder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.enabled = builder.enabled;
//        this.roles = builder.roles;
    }

    public static class UserBuilder {
        private String email;
        private String password;
        private Boolean enabled;
//        private Set<Role> roles = new HashSet<>();
        private Role role;

        public User build() {
            return new User(this);
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }
        public UserBuilder enabled(Boolean isEnabled) {
            this.enabled = isEnabled;
            return this;
        }
        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

    }

}
