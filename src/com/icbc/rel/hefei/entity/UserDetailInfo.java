package com.icbc.rel.hefei.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * 融e联用户信息表
 */
public class UserDetailInfo {
    private Integer iid;

    private String mpId;//公众号id

    private String imUserId;//用户融e联id

    private String province;//省份
    
    private String city;//城市

    private String nickName;//昵称

    private String sex;//性别 值为1时是男性，值为0时是女性

    private String icbcUserId;//统一通行证号

    private String unino;//统一认证号

    private String mobileNo;//手机号

    private String customerType;//是否实名：1实名; 2非实名，可能空也认为非实名

    private String cisno;//客户信息号
    
    private String starLevel;//用户星级
    
    private String registerTime;//注册时间
    
    private Date createTime;//创建时间

    
    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

   

    public String getUnino() {
        return unino;
    }

    public void setUnino(String unino) {
        this.unino = unino;
    }

    public String getIcbcUserId() {
        return icbcUserId;
    }

    public void setIcbcUserId(String icbcUserId) {
        this.icbcUserId = icbcUserId;
    }

   

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getMpId() {
		return mpId;
	}

	public void setMpId(String mpId) {
		this.mpId = mpId;
	}

	public String getImUserId() {
		return imUserId;
	}

	public void setImUserId(String imUserId) {
		this.imUserId = imUserId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCisno() {
		return cisno;
	}

	public void setCisno(String cisno) {
		this.cisno = cisno;
	}

	public String getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	@Override
	public String toString() {
		return "UserDetailInfo [iid=" + iid + ", mpId=" + mpId + ", imUserId=" + imUserId + ", province=" + province
				+ ", city=" + city + ", nickName=" + nickName + ", sex=" + sex + ", icbcUserId=" + icbcUserId
				+ ", unino=" + unino + ", mobileNo=" + mobileNo + ", customerType=" + customerType + ", cisno=" + cisno
				+ ", starLevel=" + starLevel + ", registerTime=" + registerTime + ", createTime=" + createTime + "]";
	}

	
    
}