package bitcamp.java106.pms.domain;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TravelAccompanyRegi implements Serializable {
    private static final long serialVersionUID = 1L;

    private int cno;
    private String mTitle;
    private String pDesc;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date sDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date eDate;
    
    private String mTem;
    private String mWithNum;
    @Override
    public String toString() {
        return "TravelAccompanyRegi [cno=" + cno + ", mTitle=" + mTitle + ", pDesc=" + pDesc + ", sDate=" + sDate
                + ", eDate=" + eDate + ", mTem=" + mTem + ", mWithNum=" + mWithNum + "]";
    }
    public int getCno() {
        return cno;
    }
    public void setCno(int cno) {
        this.cno = cno;
    }
    public String getmTitle() {
        return mTitle;
    }
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public String getpDesc() {
        return pDesc;
    }
    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }
    public Date getsDate() {
        return sDate;
    }
    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }
    public Date geteDate() {
        return eDate;
    }
    public void seteDate(Date eDate) {
        this.eDate = eDate;
    }
    public String getmTem() {
        return mTem;
    }
    public void setmTem(String mTem) {
        this.mTem = mTem;
    }
    public String getmWithNum() {
        return mWithNum;
    }
    public void setmWithNum(String mWithNum) {
        this.mWithNum = mWithNum;
    }
   
    
    
}