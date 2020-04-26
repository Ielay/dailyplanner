package com.dailyplanner.task.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * The class represents some person task to do
 *
 * @author lelay
 * @since 18.04.20
 */
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    //UTC
    //convert to timestamp?
    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "done")
    private Boolean done;

    //UTC
    //convert to timestamp?
    @Column(name = "completion_time")
    private Date completionTime;
}
