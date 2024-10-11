package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Torniquete {
    private int id;
    private String name;
    private String ip;
    @JsonProperty("branch_id")
    private List<Object> branchId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<Object> getBranchId() {
        return branchId;
    }

    public void setBranchId(List<Object> branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "Torniquete{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", branchId=" + branchId +
                '}';
    }
}
