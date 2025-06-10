package login.example.demoSpringBootLab1.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @Size(max = 10)
    @Column(name = "medico_id", nullable = false, length = 10)
    private String medicoId;

    @Size(max = 6)
    @Column(name = "costoconsulta", length = 6)
    private String costoconsulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localidad")
    private Localidad localidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad")
    private Especialidad especialidad;

    @Size(max = 99)
    @Column(name = "horariosemanal", length = 99)
    private String horariosemanal;

    @Column(name = "frecuencia")
    private Integer frecuencia;

    @Column(name = "foto")
    private String foto;

    @Column(name = "presentacion", columnDefinition = "TEXT")
    private String presentacion;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId  // Indica que 'medicoId' se usará también como clave foránea
    @JoinColumn(name = "medico_id")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Usuario usuario;
}
