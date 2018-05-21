package com.cnksi.yw.model;

import java.util.List;

public class YlogVo {

    /**
     * status : ok
     * start_time:"2018-04-16"
     * end_time:"2018-04-22"
     * projectName : ["乐山风控","广元风控","天府新区风控","资阳风控","南充风控"]
     * series : [{"name":"客户使用","data":[5,3,4,7,2]},{"name":"程序BUG","data":[2,2,3,2,1]},{"name":"软件设计","data":[3,4,4,2,5]},{"name":"实施部署","data":[3,4,4,2,5]},{"name":"数据处理","data":[3,4,4,2,5]},{"name":"软件质量","data":[3,4,4,2,5]},{"name":"出差","data":[3,4,4,2,15]},{"name":"培训","data":[3,4,4,2,5]},{"name":"日常工作","data":[3,4,4,2,15]}]
     */

    private String status;
    private List<String> projectName;
    private List<SeriesBean> series;
    private String start_time;
    private String end_time;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getProjectName() {
        return projectName;
    }

    public void setProjectName(List<String> projectName) {
        this.projectName = projectName;
    }

    public List<SeriesBean> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesBean> series) {
        this.series = series;
    }

    public static class SeriesBean {
        /**
         * name : 客户使用
         * data : [5,3,4,7,2]
         */

        private String name;
        private List<Double> data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Double> getData() {
            return data;
        }

        public void setData(List<Double> data) {
            this.data = data;
        }
    }
}
