package com.anfeng.infocollection.model;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ErrorModel
{

    /**
     * deviceinfo : {"phone_brand":"samsung","phone_model":"SAMSUNG-SM-N900A","system_version":"4.4.2","sdk_version":"19","versionName":"4.5.1","versionCode":"1"}
     * error_summer : java.lang.IllegalStateException: Activity has been destroyed
     * error_msg : {"error_path":"android.support.v4.app.FragmentManagerImpl","error_classname":"FragmentManager.java","error_method":"enqueueAction","error_location":"第1864行"}
     * error_time : 2017-03-20 16:13:47
     */

    private DeviceinfoBean deviceinfo;
    private String error_summer;
    private ErrorMsgBean error_msg;
    private String error_time;

    public DeviceinfoBean getDeviceinfo()
    {
        return deviceinfo;
    }

    public void setDeviceinfo(DeviceinfoBean deviceinfo)
    {
        this.deviceinfo = deviceinfo;
    }

    public String getError_summer()
    {
        return error_summer;
    }

    public void setError_summer(String error_summer)
    {
        this.error_summer = error_summer;
    }

    public ErrorMsgBean getError_msg()
    {
        return error_msg;
    }

    public void setError_msg(ErrorMsgBean error_msg)
    {
        this.error_msg = error_msg;
    }

    public String getError_time()
    {
        return error_time;
    }

    public void setError_time(String error_time)
    {
        this.error_time = error_time;
    }

    public static class DeviceinfoBean
    {
        /**
         * phone_brand : samsung
         * phone_model : SAMSUNG-SM-N900A
         * system_version : 4.4.2
         * sdk_version : 19
         * versionName : 4.5.1
         * versionCode : 1
         */

        private String phone_brand;
        private String phone_model;
        private String system_version;
        private String sdk_version;
        private String versionName;
        private String versionCode;

        public String getPhone_brand()
        {
            return phone_brand;
        }

        public void setPhone_brand(String phone_brand)
        {
            this.phone_brand = phone_brand;
        }

        public String getPhone_model()
        {
            return phone_model;
        }

        public void setPhone_model(String phone_model)
        {
            this.phone_model = phone_model;
        }

        public String getSystem_version()
        {
            return system_version;
        }

        public void setSystem_version(String system_version)
        {
            this.system_version = system_version;
        }

        public String getSdk_version()
        {
            return sdk_version;
        }

        public void setSdk_version(String sdk_version)
        {
            this.sdk_version = sdk_version;
        }

        public String getVersionName()
        {
            return versionName;
        }

        public void setVersionName(String versionName)
        {
            this.versionName = versionName;
        }

        public String getVersionCode()
        {
            return versionCode;
        }

        public void setVersionCode(String versionCode)
        {
            this.versionCode = versionCode;
        }
    }

    public static class ErrorMsgBean
    {
        /**
         * error_path : android.support.v4.app.FragmentManagerImpl
         * error_classname : FragmentManager.java
         * error_method : enqueueAction
         * error_location : 第1864行
         */

        private String error_path;
        private String error_classname;
        private String error_method;
        private String error_location;

        public String getError_path()
        {
            return error_path;
        }

        public void setError_path(String error_path)
        {
            this.error_path = error_path;
        }

        public String getError_classname()
        {
            return error_classname;
        }

        public void setError_classname(String error_classname)
        {
            this.error_classname = error_classname;
        }

        public String getError_method()
        {
            return error_method;
        }

        public void setError_method(String error_method)
        {
            this.error_method = error_method;
        }

        public String getError_location()
        {
            return error_location;
        }

        public void setError_location(String error_location)
        {
            this.error_location = error_location;
        }
    }
}
