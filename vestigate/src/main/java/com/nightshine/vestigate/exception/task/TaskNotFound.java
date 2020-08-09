package com.nightshine.vestigate.exception.task;

@SuppressWarnings("serial")
public class TaskNotFound extends Exception {
    public TaskNotFound(String error){
        super(error);
    }
}
