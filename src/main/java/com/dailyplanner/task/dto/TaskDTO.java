package com.dailyplanner.task.dto;

/**
 * @author lelay
 * @since 27.04.20
 */
public class TaskDTO {

    public Long taskId;

    public String title;

    public String description;

    //timestamp
    public Long deadline;

    public Boolean done;

    //timestamp
    public Long completionTime;

    public Long userId;
}
