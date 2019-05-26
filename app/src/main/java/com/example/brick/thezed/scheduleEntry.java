package com.example.brick.thezed;

public class scheduleEntry {
    private String title;
    private String dueDate;

    public scheduleEntry(String title, String dueDate){
            this.title = title;
            this.dueDate = dueDate;
    }

    public String getTitle(){
        return title;
    }

    public String getDueDate(){
        return dueDate;
    }

}
