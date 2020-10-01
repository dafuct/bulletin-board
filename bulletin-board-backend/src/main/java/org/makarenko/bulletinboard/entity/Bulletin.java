package org.makarenko.bulletinboard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bulletins")
public class Bulletin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  private String imagePath;

  @Column(nullable = false, length = 50)
  @NotBlank
  @Size(min = 1, max = 50)
  private String title;

  @Column(nullable = false, length = 500)
  @NotBlank
  @Size(min = 1, max = 500)
  private String description;

  @Column(nullable = false)
  @NotNull
  @PastOrPresent
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime publishDate;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

}
