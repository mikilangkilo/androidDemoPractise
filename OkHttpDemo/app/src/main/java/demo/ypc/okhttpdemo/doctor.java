package demo.ypc.okhttpdemo;

/**
 * Created by yinpengcheng on 2017/9/1.
 */

public class doctor {
    String DoctorId;
    String DeptL1Code;
    String DeptL2;
    String DeptL1Name;
    String Name;
    String ProfessionGrade;
    String Description;
    String OutpationTime;
    String Photo;
    String DetailInfo;
    int ObjectEntryState;

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }

    public String getDeptL1Code() {
        return DeptL1Code;
    }

    public void setDeptL1Code(String deptL1Code) {
        DeptL1Code = deptL1Code;
    }

    public String getDeptL2() {
        return DeptL2;
    }

    public void setDeptL2(String deptL2) {
        DeptL2 = deptL2;
    }

    public String getDeptL1Name() {
        return DeptL1Name;
    }

    public void setDeptL1Name(String deptL1Name) {
        DeptL1Name = deptL1Name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProfessionGrade() {
        return ProfessionGrade;
    }

    public void setProfessionGrade(String professionGrade) {
        ProfessionGrade = professionGrade;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOutpationTime() {
        return OutpationTime;
    }

    public void setOutpationTime(String outpationTime) {
        OutpationTime = outpationTime;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getDetailInfo() {
        return DetailInfo;
    }

    public int getObjectEntryState() {
        return ObjectEntryState;
    }

    public void setObjectEntryState(int objectEntryState) {
        ObjectEntryState = objectEntryState;
    }

    public void setDetailInfo(String detailInfo) {
        DetailInfo = detailInfo;
    }

}
