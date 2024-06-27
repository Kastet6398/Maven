package org.hillel.homework.entity;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppEntity {
    private String name;
    private Date releaseDate;
    private double rating;
    private double cost;
    private String description;
    private Timestamp creationDate;
    private String type;
    private long id;
}
