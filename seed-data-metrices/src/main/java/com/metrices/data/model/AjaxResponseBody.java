package com.metrices.data.model;

import java.util.List;

public class AjaxResponseBody {

    private String msg;
    private List<DataMetricsProject> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataMetricsProject> getResult() {
        return result;
    }

    public void setResult(List<DataMetricsProject> result) {
        this.result = result;
    }

}
