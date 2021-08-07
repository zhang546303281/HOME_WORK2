package com.hw.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("`t_user_base_info`")
public class UserInfo implements Serializable {

    @TableId(type = IdType.INPUT)
    private String uuid;

    private String userId;

    private String userName;

    private Date createDate;

    private Date updateDate;

    private String createBy;

    private String updateBy;

    private String logicId;

    private String logicFlag;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getLogicId() {
        return logicId;
    }

    public void setLogicId(String logicId) {
        this.logicId = logicId;
    }

    public String getLogicFlag() {
        return logicFlag;
    }

    public void setLogicFlag(String logicFlag) {
        this.logicFlag = logicFlag;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("uuid='").append(uuid).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", createBy='").append(createBy).append('\'');
        sb.append(", updateBy='").append(updateBy).append('\'');
        sb.append(", logicId='").append(logicId).append('\'');
        sb.append(", logicFlag='").append(logicFlag).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
