package com.example.brick.thezed.TheZed.importance;



import com.example.brick.thezed.TheZed.activity.ActivityTypes;
import com.example.brick.thezed.TheZed.activity.DefaultActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * @author Brick
 * Takes entire schedule
 * and reorders arraylist in order of importance
 */
public class ImportanceScale {
    private double importance;
    private double urgencyRating;
    private double subjectValue = 0.0;
    private ArrayList<DefaultActivity> NewList;
    //common word, Count
    private HashMap<String, Double> sleepMap, mealMap, testMap, homeWorkMap, skillTrainingMap, projectBuildingMap, entertainmentMap, generalStudyMap, socialTimeMap, anomalyMap;
    private HashMap<String, Integer> countMap;


    /**
     * @param master   **generic list from controller**
     * @param countMap **number of each activity**
     */
    public ImportanceScale(ArrayList<DefaultActivity> master, HashMap countMap) {
        NewList = master;
        this.countMap = countMap;
        setImportance(master);


    }

    /**
     * reorders list into order of importance
     *
     * @param scheduleList **master schedule**
     */
    public void orderList(ArrayList<DefaultActivity> scheduleList) {

    }

    /**
     * uses equation with factors from subclasses to determine importance
     * equation: (A*T^2 + I^E)/(N + (P - E*I))
     *
     * @param i **one activity**
     * @return importance value for one activity
     */
    public double getImportance(DefaultActivity i) {
        double importanceValue = 0.0;
        double activityTypeFactor = i.getSubjectValue();
        double urgencyFactor = i.getUrgencyFactor();
        //double intrinsicFactor = i.getIntrinsicFactor();
        double enjoymentFactor = i.getEnjoymentFactor();
        double activityCount = countMap.get(i.getActivityType());
        //double proficiencyfactor =
        //double temp = (i.getSubjectValue() *

        return importanceValue;
    }

    /**
     * @param event **one activity**
     * @return UrgencyFactor **Based on time til dueDate**
     */
    public double getUrgencyRating(DefaultActivity event) {
        Date currentTime = Calendar.getInstance().getTime();
        return 0;
    }

    public double getEnjoymentFactor(DefaultActivity event) {
        return 0;
    }

    /**
     * @param i **one activity**
     * @return ActivityValue **static value for Activity Type**
     */
    public double getSubjectValue(DefaultActivity i) {
        switch (i.getActivityType()) {
            case SLEEP:
                subjectValue = 10;
                break;
            case MEAL:
                subjectValue = 9;
                break;
            case TESTSTUDY:
                subjectValue = 8;
                break;
            case HOMEWORK:
                subjectValue = 7;
                break;
            case SKILLTRAINING:
                subjectValue = 6;
                break;
            case PROJECTBUILDING:
                subjectValue = 5;
                break;
            case ENTERTAINMENT:
                subjectValue = 4;
                break;
            case GENERALSTUDY:
                subjectValue = 4;
                break;
            case SOCIALTIME:
                subjectValue = 10;
                break;
            case ANOMALY:
                subjectValue = 0;
                break;
            default:
                System.out.println("Bad Input");
        }
        return subjectValue;
    }

    /**
     * @param dueDate
     */
    public void setUrgencyRating(double dueDate) {

    }

    /**
     * @param newSubjectValue **used for changing set value**
     * @param i               **one activity**
     */
    public void setSubjectValue(double newSubjectValue, DefaultActivity i) {
        i.setSubjectValue(newSubjectValue);
        i.setTypeSwitch(true);
    }

    /**
     * sets values for each activity in masterlist so that getImportance can be called
     *
     * @param scheduleList **master list**
     */
    public void setImportance(ArrayList<DefaultActivity> scheduleList) {
        for (DefaultActivity i : scheduleList) {
            getKeyWords(i);
            i.setUrgencyFactor(getUrgencyRating(i));
            i.setEnjoymentFactor(getEnjoymentFactor(i));
            if (!i.getTypeSwitch()) {
                i.setSubjectValue(getSubjectValue(i));
            }
        }
        for (DefaultActivity i : scheduleList) {
            i.setProficiencyFactor(getProficiency(i));
            i.setImportanceValue(getImportance(i));
        }
    }

    /**
     * was messing around with some logic
     * called after getKeywords. Compares words in title to activity hashmap.
     * multiplies words by 100 then returns ave as proficiency
     *
     * @param i **one activity**
     * @return **proficiencyFactor 0-100**
     */
    public double getProficiency(DefaultActivity i) {
        ActivityTypes subType = i.getActivityType();
        double proficiency = 0.0;
        int count = 1;
        switch (subType) {
            case SLEEP: //day
                for (String keyValue : i.getName().split(" ")) {
                    if (keyValue.length() >= 3) {
                        if (sleepMap.get(keyValue) != null) {
                            if (sleepMap.get(keyValue) <= 3) {
                                proficiency += sleepMap.get(keyValue) * 100;
                                count += sleepMap.get(keyValue);
                            }
                        } else {
                            proficiency += 30;
                            count++;
                        }
                        proficiency += proficiency / count;
                    }
                }
                return proficiency;
            case MEAL: //day
                for (String keyValue : i.getName().split(" ")) {
                    if (keyValue.length() >= 4) {
                        if (mealMap.get(keyValue) != null) {
                            if (mealMap.get(keyValue) >= 4) {
                                proficiency += mealMap.get(keyValue) * 100;
                                count += mealMap.get(keyValue);
                            }
                        } else {
                            proficiency += 30;
                        }
                        proficiency += proficiency / count;
                    }
                }
                return proficiency;
            case TESTSTUDY: //week
                for (String keyValue : i.getName().split(" ")) {
                    if (keyValue.length() >= 4) {
                        if (testMap.get(keyValue) != null) {
                            if (testMap.get(keyValue) > 4) {
                                proficiency += testMap.get(keyValue) * 100;
                                count += testMap.get(keyValue);
                            }
                        } else {
                            proficiency += 25;
                        }
                        proficiency += proficiency / count;
                    }
                }
                return proficiency;
            default:
                return 50;
        }
    }

    /**
     * parses title of activity to find keywords
     * sends it to a map of all keywords in that activity
     *
     * @param schedule **one activity**
     */
    public void getKeyWords(DefaultActivity schedule) {
        ActivityTypes subType = schedule.getActivityType();
        switch (subType) {
            case SLEEP:
                for (String keyValue : schedule.getName().split(" ")) {
                    if (keyValue.length() >= 3) {
                        if (sleepMap.get(keyValue) != null) {
                            sleepMap.put(keyValue, sleepMap.get(keyValue) + 1);
                        } else {
                            sleepMap.put(keyValue, 1.0);
                        }
                    }
                }

                break;
            case MEAL:
                for (String keyValue : schedule.getName().split(" ")) {
                    if (keyValue.length() >= 4) {
                        if (mealMap.get(keyValue) != null) {
                            mealMap.put(keyValue, mealMap.get(keyValue) + 1);
                        }
                    } else {
                        mealMap.put(keyValue, 1.0);
                    }
                }
                break;
            case TESTSTUDY:
                for (String keyValue : schedule.getName().split(" ")) {
                    if (keyValue.length() >= 4) {
                        if (testMap.get(keyValue) != null) {
                            testMap.put(keyValue, testMap.get(keyValue) + 1);
                        }
                    } else {
                        testMap.put(keyValue, 1.0);
                    }
                }
                break;
            case HOMEWORK:
                for (String keyValue : schedule.getName().split(" ")) {
                    if (keyValue.length() >= 4) {
                        if (homeWorkMap.get(keyValue) != null) {
                            homeWorkMap.put(keyValue, homeWorkMap.get(keyValue) + 1);
                        }
                    } else {
                        homeWorkMap.put(keyValue, 1.0);
                    }
                }
                break;
            case SKILLTRAINING:
                for (String keyValue : schedule.getName().split(" ")) {
                    if (keyValue.length() >= 4) {
                        if (skillTrainingMap.get(keyValue) != null) {
                            skillTrainingMap.put(keyValue, skillTrainingMap.get(keyValue) + 1);
                        }
                    } else {
                        skillTrainingMap.put(keyValue, 1.0);
                    }
                }
                break;
            case PROJECTBUILDING:
                for (String keyValue : schedule.getName().split(" ")) {
                    if (projectBuildingMap.get(keyValue) != null) {
                        projectBuildingMap.put(keyValue, projectBuildingMap.get(keyValue) + 1);
                    } else {
                        projectBuildingMap.put(keyValue, 1.0);
                    }
                }
                break;
            case ENTERTAINMENT:
                for (String keyValue : schedule.getName().split(" ")) {
                    if (entertainmentMap.get(keyValue) != null) {
                        entertainmentMap.put(keyValue, entertainmentMap.get(keyValue) + 1);
                    } else {
                        entertainmentMap.put(keyValue, 1.0);
                    }
                }
                break;
            case GENERALSTUDY:
                for (String keyValue : schedule.getName().split(" ")) {
                    if (generalStudyMap.get(keyValue) != null) {
                        generalStudyMap.put(keyValue, generalStudyMap.get(keyValue) + 1);
                    } else {
                        generalStudyMap.put(keyValue, 1.0);
                    }
                }
                break;
            case SOCIALTIME:
                for (String keyValue : schedule.getName().split(" ")) {
                    if (socialTimeMap.get(keyValue) != null) {
                        socialTimeMap.put(keyValue, socialTimeMap.get(keyValue) + 1);
                    } else {
                        socialTimeMap.put(keyValue, 1.0);
                    }
                }
                break;
            case ANOMALY:
                for (String keyValue : schedule.getName().split(" ")) {
                    if (anomalyMap.get(keyValue) != null) {
                        anomalyMap.put(keyValue, anomalyMap.get(keyValue) + 1);
                    } else {
                        anomalyMap.put(keyValue, 1.0);
                    }
                }
                break;
            default:

        }
    }
}
