package com.example.brick.thezed;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class scheduleEntry implements Parcelable, Comparable<scheduleEntry> {
    private String title;
    private String description;
    private Date dueDate;
    private Date initialDate;
    private String activityType;
    private Integer imageView;
    private static final String TAG = "scheduleEntry";

    public scheduleEntry() {
    }

    public scheduleEntry(String title, String description, String activityType, Date initialDate, Date dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.initialDate = initialDate;
        this.activityType = activityType;
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

    public Date getInitialDate() {
        return initialDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getStringDueDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd ,yyyy", Locale.US);
        return simpleDateFormat.format(getDueDate());
    }

    public String getStringInitialDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd ,yyyy", Locale.US);
        return simpleDateFormat.format(getInitialDate());
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
        if (obj instanceof scheduleEntry) {
            scheduleEntry i = (scheduleEntry) obj;
            return (this.title.equals(i.title) && (this.description.equals(i.description)) && (this.initialDate).equals(i.initialDate) && ((this.dueDate.equals(i.dueDate)))
                    && (this.activityType.equals(i.activityType)));
        }
        return false;
    }

    protected scheduleEntry(Parcel in) {
        title = in.readString();
        description = in.readString();
        long tmpDueDate = in.readLong();
        dueDate = tmpDueDate != -1 ? new Date(tmpDueDate) : null;
        long tmpInitialDate = in.readLong();
        initialDate = tmpInitialDate != -1 ? new Date(tmpInitialDate) : null;
        activityType = in.readString();
        imageView = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(dueDate != null ? dueDate.getTime() : -1L);
        dest.writeLong(initialDate != null ? initialDate.getTime() : -1L);
        dest.writeString(activityType);
        if (imageView == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(imageView);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<scheduleEntry> CREATOR = new Parcelable.Creator<scheduleEntry>() {
        @Override
        public scheduleEntry createFromParcel(Parcel in) {
            return new scheduleEntry(in);
        }

        @Override
        public scheduleEntry[] newArray(int size) {
            return new scheduleEntry[size];
        }
    };

    @Override
    public int compareTo(scheduleEntry scheduleEntry) {
        return getDueDate().compareTo(scheduleEntry.getDueDate());
    }
}