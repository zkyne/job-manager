package com.zkyne.jobmanager.common.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Page<T> implements Serializable {

    private static final long serialVersionUID = 7509254477412002939L;
    /** 数据*/
    private List<T> result = Collections.emptyList();
    /** 总数据数*/
    private int count;
    /** 当前页 */
    private int curpage = 1;
    /** 总页数,即 最后一页*/
    private int pagecount;
    /** 每页的大小*/
    private int pagesize = 10;
    /** 前一页*/
    private int prev;
    /** 后一页*/
    private int next;
    /** 第一页*/
    private int first;

    private String uri;
    private Map<String, String> query;
    
    private int pageBtn = 10;  	//页面上显示多少页
    private Integer startBtn = 1;	//动态分页的开始页面数
    private Integer endBtn = 1;	//动态分页的结束页面数

    public Page() {}

    public Page(final int curpage) {
        this.curpage = curpage;
    }

    private void calculate() {
        //总页数
        int r = count % pagesize;
        this.pagecount = r > 0 ? (count / pagesize + 1) : count / pagesize;
        this.first = 1;

        this.prev = this.curpage <= this.first ? 0 : this.curpage - 1;
        this.next = this.curpage >= this.pagecount ? 0 : this.curpage + 1;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public void setCount(int count) {
        this.count = count;
        if (count > 0)
            calculate();
    }

    public void setCurpage(int curpage) {
        if (curpage > this.curpage)
            this.curpage = curpage;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
    
    
    public List<T> getResult() {
        return result;
    }

    
    public int getCount() {
        return count;
    }

    
    public int getCurpage() {
        return curpage;
    }

    
    public int getPagecount() {
        return pagecount;
    }

    
    public int getPagesize() {
        return pagesize;
    }

    
    public int getPrev() {
        return prev;
    }

    
    public int getNext() {
        return next;
    }

    
    public int getFirst() {
        return first;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getQuery() {
        return query;
    }
    
    public String getQueryParams() {
        StringBuilder params = new StringBuilder();
        if(!DataUtil.isEmpty(query)){
            Set<Map.Entry<String, String>> entrySet = this.query.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            return params.toString();
        }else{
            return params.append("").toString();
        }
    }    

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    
    public int getCursor() {
        if (curpage > 0)
            return (curpage - 1) * pagesize;
        return 0;
    }

	public void setPageButton(int pageButton) {
		this.pageBtn = pageButton;
	}
    
	/**
     * 提供私有的动态页面算法,页面数的动态显示
     */
    private void activePage(){
        //相对总页面进行判断是否小于指定的10页
        if(pagecount < pageBtn) {
            //总页面数小雨指定的的10页
            startBtn = 1;
            endBtn = pagecount;
        }else{
            //总页面数大于指定的10页
        	int startIndex = (int) (0.4 * pageBtn);
        	int endIndex = pageBtn-1-startIndex;
            startBtn = curpage - startIndex;//显示当前页面的前四页
            endBtn = curpage + endIndex;//显示当前页面的后五页
            if(startBtn < 1) {
                //当前页面前面不够四页
                startBtn = 1;
                endBtn = pageBtn;
            }
            if(endBtn > pagecount) {
                //当前页面后面不够五页
                startBtn = pagecount - pageBtn + 1;
                endBtn = pagecount;

                /*startBtn = pagecount - 9;
                endBtn = pagecount;*/
            }
        }
    }

    public Integer getEndBtn() {
        //end由算法得出,获取前先调用计算方法
        activePage();
        return endBtn;
    }

    public Integer getStartBtn() {
        //start有算法得出,获取前得先调用计算方法
        activePage();
        return startBtn;
    }

    /**
     * 页面使用格式
     * <nav style="height: auto;text-align: right;">${page}</nav>
     * @return
     */
    public String toString() {
        if(this.count > this.pagesize){
            StringBuilder html = new StringBuilder();
//        html.append("<nav style='height: auto;text-align: right;'>");
            html.append("<ul class='pagination' id='page2'>");
            if (this.getCurpage() > 1) {
                html.append("<li><a href='javascript:void(0);' onclick='clickPage(\"").append(getPageUrl(1)).append("\")' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
                html.append("<li class='previous'><a href='javascript:void(0);' onclick='clickPage(\"").append(getPageUrl(this.getCurpage() - 1)).append("\")'>上一页</a></li>");
            }
            for (int i = this.getStartBtn(); i <= this.getEndBtn(); i++) {
                if (this.getCurpage() == i) {
                    html.append("<li class='active' page='").append(i).append("'><a href='javascript:void(0);'>").append(i).append("</a></li>");
                } else {
                    html.append("<li page='").append(i).append("'><a href='javascript:void(0);' onclick='clickPage(\"").append(getPageUrl(i)).append("\")'>").append(i).append("</a></li>");
                }
            }
            if (this.getCurpage() < this.getPagecount()) {
                html.append("<li class='next'><a href='javascript:void(0);' onclick='clickPage(\"").append(getPageUrl(this.getCurpage() + 1)).append("\")'>下一页</a></li>");
                html.append("<li><a href='javascript:void(0);' onclick='clickPage(\"").append(getPageUrl(this.getPagecount())).append("\")' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
            }
            html.append("<li class='next'><a href='javascript:void(0);'>共").append(this.getPagecount()).append("页</a></li>");
            html.append("</ul>");
//        html.append("</nav>");
            return html.toString();
        }
        return "";
    }

    private String getPageUrl(int curpage) {
        Map<String, String> query = this.getQuery();
        query.put("pageNo", String.valueOf(curpage));
        query.put("pageSize", String.valueOf(this.getPagesize()));
        return this.getUri() + "?" + RequestUtil.toQueryString(query);
    }
}
