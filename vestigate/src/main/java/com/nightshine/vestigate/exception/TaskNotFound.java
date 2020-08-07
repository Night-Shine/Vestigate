package com.nightshine.vestigate.exception;

@SuppressWarnings("serial")
public class TaskNotFound extends Exception {
    public TaskNotFound(String error){
        super(error);
    }
}
