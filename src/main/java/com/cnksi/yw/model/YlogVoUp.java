package com.cnksi.yw.model;

import java.util.Date;
import java.util.List;

public class YlogVoUp {


    /**
     * status :  ok
     * data : [{"email":"547566346 @qq.com","project_name":"国网四川省电力公司宜宾供电公司安全风险管控平台维护","job_content":"调试数据同步相关配置","job_duration":5,"jb_date":"2018 - 04 - 18","type_name":"运维"},{"email":"547566346 @qq.com","project_name":"国网四川省电力公司宜宾供电公司安全风险管控平台维护","job_content":"调试数据同步相关配置","job_duration":5,"jb_date":"2018 - 04 - 18","type_name":"运维"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * email : 547566346 @qq.com
         * project_name : 国网四川省电力公司宜宾供电公司安全风险管控平台维护
         * job_content : 调试数据同步相关配置
         * job_duration : 5.0
         * jb_date : 2018 - 04 - 18
         * type_name : 运维
         */

        private String email;
        private String project_name;
        private String job_content;
        private double job_duration;
        private Date jb_date;
        private String type_name;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getJob_content() {
            return job_content;
        }

        public void setJob_content(String job_content) {
            this.job_content = job_content;
        }

        public double getJob_duration() {
            return job_duration;
        }

        public void setJob_duration(double job_duration) {
            this.job_duration = job_duration;
        }

        public Date getJb_date() {
            return jb_date;
        }

        public void setJb_date(Date jb_date) {
            this.jb_date = jb_date;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
}
