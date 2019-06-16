package com.example.brick.thezed;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleEntry implements Parcelable, Comparable<ScheduleEntry> {
    private String title;
    private String description;
    private LocalDateTime fullDueDate;
    private LocalDate initialDate;
    private String activityType;
    private Integer imageView;
    private int id;
    private static final String TAG = "ScheduleEntry";

    public ScheduleEntry() {
    }

    public ScheduleEntry( String title, String description, String activityType, LocalDate initialDate, LocalDateTime fullDueDate) {
        this.title = title;
        this.description = description;
        this.activityType = activityType;
        this.initialDate = initialDate;
        this.fullDueDate = fullDueDate;
    }
    public int getId(){
        return id;
    }
    public void setId(long id){
        this.id = (int) id;
    }
    public void setImageView(Integer img) {
        imageView = img;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }
    public LocalDateTime getFullDueDate() {
        return fullDueDate;
    }

    public String getStringInitialDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return getInitialDate().format(formatter);
    }

    public String getStringFullDueDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        LocalDateTime localDateTime = getFullDueDate();
        return localDateTime.format(formatter);
    }

    public String getActivityType() {
        return activityType;
    }

    public Integer getImageView() {
        return imageView;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof ScheduleEntry) {
            ScheduleEntry i = (ScheduleEntry) obj;
            return (this.title.equals(i.title) && (this.description.equals(i.description)) && (this.initialDate).equals(i.initialDate) && ((this.fullDueDate.equals(i.fullDueDate)))
                    && (this.activityType.equals(i.activityType)) && this.id == i.id);
        }
        return false;
    }

    protected ScheduleEntry(Parcel in) {
        title = in.readString();
        description = in.readString();
        fullDueDate = (LocalDateTime) in.readValue(LocalDateTime.class.getClassLoader());
        initialDate = (LocalDate) in.readValue(LocalDate.class.getClassLoader());
        activityType = in.readString();
        imageView = in.readByte() == 0x00 ? null : in.readInt();
        id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeValue(fullDueDate);
        dest.writeValue(initialDate);
        dest.writeString(activityType);
        if (imageView == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(imageView);
        }
        dest.writeInt(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ScheduleEntry> CREATOR = new Parcelable.Creator<ScheduleEntry>() {
        @Override
        public ScheduleEntry createFromParcel(Parcel in) {
            return new ScheduleEntry(in);
        }

        @Override
        public ScheduleEntry[] newArray(int size) {
            return new ScheduleEntry[size];
        }
    };

    @Override
    public int compareTo(ScheduleEntry scheduleEntry) {
        return getFullDueDate().compareTo(scheduleEntry.getFullDueDate());
    }

    public boolean isFull(){
        return getActivityType() != null && getTitle() != null && getFullDueDate() != null
                && getInitialDate() != null && getDescription() != null;
    }
}