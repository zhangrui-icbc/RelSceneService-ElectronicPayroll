package com.icbc.rel.hefei.entity.todo.salary;



import java.util.HashMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ������Ϣ����
 * 
 * @author ruoyi
 */
public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";

    /**
     * ״̬����
     */
    public enum Type
    {
        /** �ɹ� */
        SUCCESS(0),
        /** ���� */
        WARN(301),
        /** ���� */
        ERROR(500);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    /** ״̬���� */
    private Type type;

    /** ״̬�� */
    private int code;

    /** �������� */
    private String msg;

    /** ���ݶ��� */
    private Object data;

    /**
     * ��ʼ��һ���´����� AjaxResult ����ʹ���ʾһ������Ϣ��
     */
    public AjaxResult()
    {
    }

    /**
     * ��ʼ��һ���´����� AjaxResult ����
     * 
     * @param type ״̬����
     * @param msg ��������
     */
    public AjaxResult(Type type, String msg)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

    /**
     * ��ʼ��һ���´����� AjaxResult ����
     * 
     * @param type ״̬����
     * @param msg ��������
     * @param data ���ݶ���
     */
    public AjaxResult(Type type, String msg, Object data)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
    }

    /**
     * ���سɹ���Ϣ
     * 
     * @return �ɹ���Ϣ
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("�����ɹ�");
    }

    /**
     * ���سɹ���Ϣ
     * 
     * @param msg ��������
     * @return �ɹ���Ϣ
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * ���سɹ���Ϣ
     * 
     * @param msg ��������
     * @param data ���ݶ���
     * @return �ɹ���Ϣ
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(Type.SUCCESS, msg, data);
    }

    /**
     * ���ؾ�����Ϣ
     * 
     * @param msg ��������
     * @return ������Ϣ
     */
    public static AjaxResult warn(String msg)
    {
        return AjaxResult.warn(msg, null);
    }

    /**
     * ���ؾ�����Ϣ
     * 
     * @param msg ��������
     * @param data ���ݶ���
     * @return ������Ϣ
     */
    public static AjaxResult warn(String msg, Object data)
    {
        return new AjaxResult(Type.WARN, msg, data);
    }

    /**
     * ���ش�����Ϣ
     * 
     * @return
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("����ʧ��");
    }

    /**
     * ���ش�����Ϣ
     * 
     * @param msg ��������
     * @return ������Ϣ
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * ���ش�����Ϣ
     * 
     * @param msg ��������
     * @param data ���ݶ���
     * @return ������Ϣ
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(Type.ERROR, msg, data);
    }

    /**
     * ���ش�����Ϣ
     * 
     * @param code ������
     * @param msg ����
     * @return ������Ϣ
     */
    public static AjaxResult errorTip(String code, String msg)
    {
        AjaxResult json = new AjaxResult();
        json.put("code", code);
        json.put("msg", msg);
        return json;
    }

    
    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("code", getCode())
            .append("msg", getMsg())
            .append("data", getData())
            .toString();
    }
}
