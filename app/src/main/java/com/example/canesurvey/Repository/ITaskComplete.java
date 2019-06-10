package com.example.canesurvey.Repository;

public interface ITaskComplete {
    public <E>void OnTaskComplete(boolean success,E returnobj);
}
