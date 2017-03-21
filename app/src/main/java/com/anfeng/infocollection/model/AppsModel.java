package com.anfeng.infocollection.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public class AppsModel
{

    private List<AppsInfoBean> AppsInfo;

    public List<AppsInfoBean> getAppsInfo()
    {
        return AppsInfo;
    }

    public void setAppsInfo(List<AppsInfoBean> AppsInfo)
    {
        this.AppsInfo = AppsInfo;
    }

    public static class AppsInfoBean
    {
        /**
         * AppName : 微信
         * PackageName : com.tencent.mm
         * InstallTime : 1488784240648
         * UpdateTime : 1488784240648
         * VersionName : 6.3.18
         * VersionCode : 800
         */

        private String AppName;
        private String PackageName;
        private String InstallTime;
        private String UpdateTime;
        private String VersionName;
        private String VersionCode;

        public String getAppName()
        {
            return AppName;
        }

        public void setAppName(String AppName)
        {
            this.AppName = AppName;
        }

        public String getPackageName()
        {
            return PackageName;
        }

        public void setPackageName(String PackageName)
        {
            this.PackageName = PackageName;
        }

        public String getInstallTime()
        {
            return InstallTime;
        }

        public void setInstallTime(String InstallTime)
        {
            this.InstallTime = InstallTime;
        }

        public String getUpdateTime()
        {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime)
        {
            this.UpdateTime = UpdateTime;
        }

        public String getVersionName()
        {
            return VersionName;
        }

        public void setVersionName(String VersionName)
        {
            this.VersionName = VersionName;
        }

        public String getVersionCode()
        {
            return VersionCode;
        }

        public void setVersionCode(String VersionCode)
        {
            this.VersionCode = VersionCode;
        }
    }
}
