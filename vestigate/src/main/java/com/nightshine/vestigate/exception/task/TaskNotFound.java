package com.nightshine.vestigate.exception.task;

@SuppressWarnings("serial")
public class TaskNotFound extends RuntimeException {
    public TaskNotFound(String error){
        super(error);
    }
}
