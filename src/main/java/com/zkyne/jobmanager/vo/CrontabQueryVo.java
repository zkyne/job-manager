package com.zkyne.jobmanager.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: CrontabDto
 * @Description:
 * @Author: zkyne
 * @Date: 2018/7/25 16:50
 */
public class CrontabQueryVo {
    private String keyLike;
    private Integer pageNo;
    private Integer pageSize;

    public CrontabQueryVo(Integer pageNo, Integer pageSize) {
        if (pageNo != null) {
            if (pageNo <= 0) {
                this.pageNo = 1;
            }else{
                this.pageNo = pageNo;
            }
        }
        if (pageSize != null) {
            if (pageSize <= 0) {
                this.pageSize = 5;
            } else if (pageSize > 10) {
                this.pageSize = 10;
            }else{
                this.pageSize = pageSize;
            }
        }
    }

    public String getKeyLike() {
        return keyLike;
    }

    public void setKeyLike(String keyLike) {
        if(StringUtils.isNotBlank(keyLike)){
            this.keyLike = "%" + keyLike + "%";
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getStartIndex() {
        if (this.pageNo != null && this.pageSize != null) {
            return (this.pageNo - 1) * this.pageSize;
        }
        return null;
    }
}
