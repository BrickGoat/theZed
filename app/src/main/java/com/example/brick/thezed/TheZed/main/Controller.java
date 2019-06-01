package com.example.brick.thezed.TheZed.main;

import com.example.brick.thezed.TheZed.activity.ActivityTypes;
import com.example.brick.thezed.TheZed.activity.AnomalyActivity;
import com.example.brick.thezed.TheZed.activity.DefaultActivity;
import com.example.brick.thezed.TheZed.activity.EntertainmentActivity;
import com.example.brick.thezed.TheZed.activity.GeneralStudyActivity;
import com.example.brick.thezed.TheZed.activity.HomeWorkActivity;
import com.example.brick.thezed.TheZed.activity.MealActivity;
import com.example.brick.thezed.TheZed.activity.ProjectBuildingActivity;
import com.example.brick.thezed.TheZed.activity.SkillTrainingActivity;
import com.example.brick.thezed.TheZed.activity.SleepActivity;
import com.example.brick.thezed.TheZed.activity.SocialTimeActivity;
import com.example.brick.thezed.TheZed.activity.TestStudyActivity;

import org.apache.commons.lang3.EnumUtils;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Brick
 * 2/2/2019
 * Reads Json file and orders them into a List of importance
 */
public class Controller {
    //Generic List holding all Activities in schedule_fragment
    private ArrayList<DefaultActivity> masterSchedule;
    private HashMap<String, Integer> countMap;

    private Integer testStudyCount, mealCount, sleepCount, homeWorkCount, skillTrainingCount, projectBuildingCount, entertainmentCount, generalStudyCount, socialTimeCount, anomalyCount = 0;

    /**
     * Turns input json to Activity objects and stores it in List
     *
     * @param schedule - Any amount of json files
     */
    public Controller(File... schedule) {
        Double[] dateArray = new Double[2];
        String subject;
        String name;
        String description;
        int count = 0;
        for (File i : schedule) {
            JSONParser parser = new JSONParser();
            try {

                Object obj = parser.parse(new FileReader(i));
                JSONObject jsonObject = (JSONObject) obj;

                name = (String) jsonObject.get("name");
                description = (String) jsonObject.get("description");
                dateArray[0] = (double) jsonObject.get("initialDate");
                dateArray[1] = (double) jsonObject.get("dueDate");
                subject = (String) jsonObject.get("subject");

                masterSchedule.add(count, addActivity(name, description, dateArray, subject));
                count++;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("bin/firstActivity.json");
        Controller test = new Controller(file);


    }

    /**
     * Removes one file with same name as target
     *
     * @param target - Activity
     */
    public void RemoveActivity(DefaultActivity target) {
        int count = 0;
        for (DefaultActivity i : masterSchedule) {
            if (i.getName().equals(target.getName())) ;
            {
                masterSchedule.remove(count);
            }
        }
    }

    /**
     * @param subject - String from json
     * @return ActivityType Enum
     */
    protected ActivityTypes getEnumType(String subject) {
        if (subject != null) {
            if (EnumUtils.isValidEnum(ActivityTypes.class, subject)) {
                return ActivityTypes.valueOf(subject);
            }
        }
        return null;
    }

    /**
     * @param name
     * @param description
     * @param dateArray
     * @param subject     All Keys from json input
     * @return One Sub Activity object
     */
    public DefaultActivity addActivity(String name, String description,
                                       Double[] dateArray, String subject) {

        ActivityTypes subType = getEnumType(subject);
        switch (subType) {
            case SLEEP:
                SleepActivity sleep = new SleepActivity(name, description,
                        dateArray, subType);
                sleepCount++;
                countMap.put("SLEEP", sleepCount);
                return sleep;
            case MEAL:
                MealActivity meal = new MealActivity(name, description,
                        dateArray, subType);
                mealCount++;
                countMap.put("MEAL", mealCount);
                return meal;
            case TESTSTUDY:
                TestStudyActivity test = new TestStudyActivity(name, description,
                        dateArray, subType);
                testStudyCount++;
                countMap.put("TESTSTUDY", testStudyCount);
                return test;
            case HOMEWORK:
                HomeWorkActivity homeWork = new HomeWorkActivity(name, description,
                        dateArray, subType);
                homeWorkCount++;
                countMap.put("HOMEWORK", homeWorkCount);
                return homeWork;
            case SKILLTRAINING:
                SkillTrainingActivity training = new SkillTrainingActivity(name,
                        description, dateArray, subType);
                skillTrainingCount++;
                countMap.put("SKILLTRAINING", skillTrainingCount);
                return training;
            case PROJECTBUILDING:
                ProjectBuildingActivity project = new ProjectBuildingActivity(name,
                        description, dateArray, subType);
                projectBuildingCount++;
                countMap.put("PROJECTBUILDING", projectBuildingCount);
                return project;
            case ENTERTAINMENT:
                EntertainmentActivity entertainment = new EntertainmentActivity(name,
                        description, dateArray, subType);
                entertainmentCount++;
                countMap.put("ENTERTAINMENT", entertainmentCount);
                return entertainment;
            case GENERALSTUDY:
                GeneralStudyActivity study = new GeneralStudyActivity(name,
                        description, dateArray, subType);
                generalStudyCount++;
                countMap.put("GENERALSTUDY", generalStudyCount);
                return study;
            case SOCIALTIME:
                SocialTimeActivity social = new SocialTimeActivity(name,
                        description, dateArray, subType);
                socialTimeCount++;
                countMap.put("SOCIALTIME", socialTimeCount);
                return social;
            case ANOMALY:
                AnomalyActivity anomaly = new AnomalyActivity(name,
                        description, dateArray, subType);
                anomalyCount++;
                countMap.put("ANOMALY", anomalyCount);
                return anomaly;
            default:

                AnomalyActivity defaultBad = new AnomalyActivity(name,
                        description, dateArray, subType);
                return defaultBad;
        }
    }


}
