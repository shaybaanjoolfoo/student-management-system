package model;

public class Grade {
    private int gradeId;
    private int studentId;
    private int courseId;
    private double gradeValue;

    public Grade() {
    }

    public Grade(int gradeId, int studentId, int courseId, double gradeValue) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.gradeValue = gradeValue;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(double gradeValue) {
        this.gradeValue = gradeValue;
    }
}