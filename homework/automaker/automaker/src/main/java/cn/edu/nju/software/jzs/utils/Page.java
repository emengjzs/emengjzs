package cn.edu.nju.software.jzs.utils;

import org.hibernate.Query;

/**
 * Created by emengjzs on 2016/4/7.
 */
public class Page {

    private int pageNo = 0;
    private int pageSize = 10;

    public static PageBuilder pageSize(int pageSize) {
        PageBuilder p = new PageBuilder();
        return p.pageSize(pageSize);
    }


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public static class PageBuilder {
        Page page;

        PageBuilder() {
            page = new Page();
        }

        public PageBuilder pageSize(int pageSize) {
            page.setPageSize(pageSize);
            return this;
        }

        public PageBuilder pageNo(int pageNo) {
            page.setPageNo(pageNo);
            return this;
        }

        public Page build() {
            return page;
        }
    }


    public Query setPage(Query query) {
        return query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize);
    }
}
