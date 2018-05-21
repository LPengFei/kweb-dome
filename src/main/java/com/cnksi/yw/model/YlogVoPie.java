package com.cnksi.yw.model;

import java.util.List;

public class YlogVoPie {


    /**
     * status : ok
     * start_time : 2018-08-01
     * end_time : 2018-09-01
     */

    private String status;
    private String start_time;
    private String end_time;
    private List<SeriesBean> series;
    private List<String> type;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public List<SeriesBean> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesBean> series) {
        this.series = series;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public static class SeriesBean {

        private Double value;
        private String name;

        public Double getValue() {
            return value;
        }

        public void setValue(Double  value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
