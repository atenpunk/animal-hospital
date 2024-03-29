/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public class PlayersModel {

    private int playerId;
    private List<YearlyModel> yearlyList;
    private String playerName;
    private int playerNumber;
    private int height;
    private int weight;
    private int positionId;
    private String positionStr;
    private Date birthday;
    private Date contractStart;
    private Date contractEnd;
    private String image;
    private String club;
    private List<VideoModel> videoModelList;
    private int createBy;
    private Date createDate;
    private int updateBy;
    private Date updateDate;
    
    // for report
    private int yearlyId;
    private double gc;
    private double annualSalary;
    private double signingFee;
    private double salaryMonth;
    private int goal;
    private int playingTime;
    private int match;
    private int win;
    private int lose;
    private int draw;
    private int starter;
    private int cleanSheet;
    private int substitution;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getContractStart() {
        return contractStart;
    }

    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }

    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getGc() {
        return gc;
    }

    public void setGc(double gc) {
        this.gc = gc;
    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    public double getSigningFee() {
        return signingFee;
    }

    public void setSigningFee(double signingFee) {
        this.signingFee = signingFee;
    }

    public double getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(double salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(int playingTime) {
        this.playingTime = playingTime;
    }

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
    }

    public int getStarter() {
        return starter;
    }

    public void setStarter(int starter) {
        this.starter = starter;
    }

    public List<VideoModel> getVideoModelList() {
        return videoModelList;
    }

    public void setVideoModelList(List<VideoModel> videoModelList) {
        this.videoModelList = videoModelList;
    }

    public List<YearlyModel> getYearlyList() {
        return yearlyList;
    }

    public void setYearlyList(List<YearlyModel> yearlyList) {
        this.yearlyList = yearlyList;
    }    

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getYearlyId() {
        return yearlyId;
    }

    public void setYearlyId(int yearlyId) {
        this.yearlyId = yearlyId;
    }

    public int getCleanSheet() {
        return cleanSheet;
    }

    public void setCleanSheet(int cleanSheet) {
        this.cleanSheet = cleanSheet;
    }

    public int getSubstitution() {
        return substitution;
    }

    public void setSubstitution(int substitution) {
        this.substitution = substitution;
    }
    
}
