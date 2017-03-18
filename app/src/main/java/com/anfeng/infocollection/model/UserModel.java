package com.anfeng.infocollection.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */

public class UserModel
{

    private List<UserBean> user;

    public List<UserBean> getUser()
    {
        return user;
    }

    public void setUser(List<UserBean> user)
    {
        this.user = user;
    }

    public static class UserBean
    {
        /**
         * id : 1
         * name : 毕烈泉
         * mobile : 15927393653
         * gender : null
         * created_at : 1489826275
         * updated_at : 1489826275
         * deleted_at : null
         * dept : 综合管理中心
         * position : 人事经理
         * email : blq@anfan.com
         * serial_no : 6641114627451280
         * nickname : s_pring
         */

        private String id;
        private String name;
        private String mobile;
        private Object gender;
        private String created_at;
        private String updated_at;
        private Object deleted_at;
        private String dept;
        private String position;
        private String email;
        private String serial_no;
        private String nickname;

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getMobile()
        {
            return mobile;
        }

        public void setMobile(String mobile)
        {
            this.mobile = mobile;
        }

        public Object getGender()
        {
            return gender;
        }

        public void setGender(Object gender)
        {
            this.gender = gender;
        }

        public String getCreated_at()
        {
            return created_at;
        }

        public void setCreated_at(String created_at)
        {
            this.created_at = created_at;
        }

        public String getUpdated_at()
        {
            return updated_at;
        }

        public void setUpdated_at(String updated_at)
        {
            this.updated_at = updated_at;
        }

        public Object getDeleted_at()
        {
            return deleted_at;
        }

        public void setDeleted_at(Object deleted_at)
        {
            this.deleted_at = deleted_at;
        }

        public String getDept()
        {
            return dept;
        }

        public void setDept(String dept)
        {
            this.dept = dept;
        }

        public String getPosition()
        {
            return position;
        }

        public void setPosition(String position)
        {
            this.position = position;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        public String getSerial_no()
        {
            return serial_no;
        }

        public void setSerial_no(String serial_no)
        {
            this.serial_no = serial_no;
        }

        public String getNickname()
        {
            return nickname;
        }

        public void setNickname(String nickname)
        {
            this.nickname = nickname;
        }
    }
}
