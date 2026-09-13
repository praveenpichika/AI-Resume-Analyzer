package com.praveen.resumeanalyzer.model;

public class DashboardStats {

    private int totalResumes;

    private double averageATS;

    private int bestJobMatch;

    private int totalSkills;

    public int getTotalResumes() {
        return totalResumes;
    }

    public void setTotalResumes(int totalResumes) {
        this.totalResumes = totalResumes;
    }

    public double getAverageATS() {
        return averageATS;
    }

    public void setAverageATS(double averageATS) {
        this.averageATS = averageATS;
    }

    public int getBestJobMatch() {
        return bestJobMatch;
    }

    public void setBestJobMatch(int bestJobMatch) {
        this.bestJobMatch = bestJobMatch;
    }

    public int getTotalSkills() {
        return totalSkills;
    }

    public void setTotalSkills(int totalSkills) {
        this.totalSkills = totalSkills;
    }

}