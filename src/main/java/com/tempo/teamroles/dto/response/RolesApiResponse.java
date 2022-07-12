package com.tempo.teamroles.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tempo.teamroles.entity.Roles;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RolesApiResponse {

    private ArrayList<Roles> content;
    private Long totalPages;
    private Long noOfElements;
    private Long totalElements;
    private Long size;
    private boolean last;

    public ArrayList<Roles> getContent() {
        return content;
    }

    public void setContent(ArrayList<Roles> content) {
        this.content = content;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getNoOfElements() {
        return noOfElements;
    }

    public void setNoOfElements(Long noOfElements) {
        this.noOfElements = noOfElements;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
