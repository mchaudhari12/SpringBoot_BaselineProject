package org.studyeasy.SpringStarter.Model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Long Id;

    @NotBlank(message = "Post Title is Required")
    private String name;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Post Body is Required")
    private String body;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name ="account_Id",referencedColumnName = "id",nullable = true)
    private Account account;
}
