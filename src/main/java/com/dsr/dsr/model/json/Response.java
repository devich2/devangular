package com.dsr.dsr.model.json;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private boolean ok;

    private List<Result> result;

    public Response(boolean ok) {
        this.ok = ok;
        this.result = null;
    }

    public Response(boolean ok, Result result) {
        this.ok = ok;
        this.result = new ArrayList<Result>();
        this.result.add(result);
    }

    public Response(boolean ok, List<Result> result) {
        this.ok = ok;
        this.result = result;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
