package com.dailyplanner.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "done")
    private Boolean done;

    @Column(name = "completion_date")
    private Date completionDate;
}
