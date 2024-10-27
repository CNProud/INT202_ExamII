package org.example.practice_exam2.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class OrderdetailId implements Serializable {
    private static final long serialVersionUID = 7761572406109839815L;
}