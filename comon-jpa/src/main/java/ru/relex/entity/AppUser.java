package ru.relex.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.relex.entity.enums.UserState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long telegrammUserId;

    @CreationTimestamp
    private LocalDateTime firstLoginDate;

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private UserState state;
}
