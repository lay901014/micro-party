package edu.sjtu.party.model.query;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 分页数据结构
 * @author: lay
 * @create: 2020/02/22 13:55
 **/
public class PageInfo {

    private static final long serialVersionUID = 3815247666005328613L;

    public PageInfo() {}

    public PageInfo(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    private int pageIndex;

    private int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public static class PageResp<T> implements Serializable {

        @Expose
        private Integer total;

        @Expose
        private List<T> data;

        @Expose
        private Object extraData;

        public PageResp() {}

        public PageResp(Integer total, List<T> data) {
            this(total, data, null);
        }

        public PageResp(Integer total, List<T> data, Object extraData) {
            this.data = data;
            this.total = total;
            this.extraData = extraData;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }

        public Object getExtraData() {
            return extraData;
        }

        public void setExtraData(Object extraData) {
            this.extraData = extraData;
        }
    }
}
