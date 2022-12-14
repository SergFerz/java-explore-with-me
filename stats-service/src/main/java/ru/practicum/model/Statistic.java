package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statistics")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app", nullable = false, length = 100)
    @Size(max = 100)
    private String app;

    @Column(name = "uri", nullable = false)
    @Size(max = 200)
    private String uri;

    @Column(name = "ip", nullable = false, length = 50)
    @Size(max = 50)
    private String ip;

    @Column(name = "stat_time", nullable = false)
    private LocalDateTime timestamp;
}