package com.example.brick.thezed.TheZed.activity;

import java.util.HashMap;

/**
 * 
 * @author Brick
 * SuperClass for all Activities
 */
public abstract class DefaultActivity {
	private Double initialDate;
	private Double dueDate;
	private String name;
	private String description;
	private ActivityTypes subject;
	private double importanceValue = 0.0;
	private double proficiencyFactor;
	private double urgencyFactor;
	private double IntrinsicValue;
	private double enjoymentFactor;
	private double subjectValue;

	/**
	 * @param name        - Title of Activity
	 * @param description - brief desc of task
	 * @param dateArray   - dates in order of initial day, deadline
	 * @param subject     - enum for specific Activity
	 */
	public DefaultActivity(String name,
						   String description, Double[] dateArray, ActivityTypes subject) {
		this.name = name;
		this.description = description;
		initialDate = dateArray[0];
		dueDate = dateArray[1];
		this.subject = subject;

	}

	public String getName() {
		return name;
	}

	public double getSubjectValue()
    {
        return subjectValue;
    }

    public double getInitialDate() {
		return initialDate;
	}

	public double getDueDate() {
		return dueDate;
	}

	public double getUrgencyFactor() {return urgencyFactor;}

	public double getEnjoymentFactor() {return enjoymentFactor;}


	public ActivityTypes getActivityType() {
		return subject;
	}

    public void setDueDate(Double newDueDate) {
        dueDate = newDueDate;
    }

    public void setProficiencyFactor(double newFactor)
    {
        proficiencyFactor = newFactor;
    }

    public void setSubjectValue(double value){
	    subjectValue = value;
    }
	public void setActivityType(ActivityTypes newSubject) {
		switch (newSubject) {
			case SLEEP:
				this.subject = ActivityTypes.SLEEP;
				break;
			case MEAL:
				this.subject = ActivityTypes.MEAL;
				break;
			case TESTSTUDY:
				this.subject = ActivityTypes.TESTSTUDY;
				break;
			case HOMEWORK:
				this.subject = ActivityTypes.HOMEWORK;
				break;
			case SKILLTRAINING:
				this.subject = ActivityTypes.SKILLTRAINING;
				break;
			case PROJECTBUILDING:
				this.subject = ActivityTypes.PROJECTBUILDING;
				break;
			case ENTERTAINMENT:
				this.subject = ActivityTypes.ENTERTAINMENT;
				break;
			case GENERALSTUDY:
				this.subject = ActivityTypes.GENERALSTUDY;
				break;
			case SOCIALTIME:
				this.subject = ActivityTypes.SOCIALTIME;
				break;
			case ANOMALY:
				this.subject = ActivityTypes.ANOMALY;
				break;
			default:
				System.out.println("Bad Input");

		}
	}

	public void setUrgencyFactor(double urgencyFactor){
		this.urgencyFactor = urgencyFactor;
	}
	public void setEnjoymentFactor(double enjoymentFactor){
		this.enjoymentFactor = enjoymentFactor;
	}
	public void setImportanceValue(double importanceValue){
		this.importanceValue = importanceValue;
	}
}