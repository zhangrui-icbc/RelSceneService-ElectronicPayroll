package com.icbc.rel.hefei.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * ��e���û���Ϣ��
 */
public class UserDetailInfo {
    private Integer iid;

    private String mpId;//���ں�id

    private String imUserId;//�û���e��id

    private String province;//ʡ��
    
    private String city;//����

    private String nickName;//�ǳ�

    private String sex;//�Ա� ֵΪ1ʱ�����ԣ�ֵΪ0ʱ��Ů��

    private String icbcUserId;//ͳһͨ��֤��

    private String unino;//ͳһ��֤��

    private String mobileNo;//�ֻ���

    private String customerType;//�Ƿ�ʵ����1ʵ��; 2��ʵ�������ܿ�Ҳ��Ϊ��ʵ��

    private String cisno;//�ͻ���Ϣ��
    
    private String starLevel;//�û��Ǽ�
    
    private String registerTime;//ע��ʱ��
    
    private Date createTime;//����ʱ��

    
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