package com.zkyne.jobmanager.common.util;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class PageTag implements TemplateDirectiveModel {
    public static ThreadLocal<Page<?>> curPage = new ThreadLocal<>();

    private Page<?> page;

    /**
     * HTTP URI GET 方式请求调用
     *
     * @param
     */
    private String handlePage() {
        initPage();
        StringBuilder html = new StringBuilder();
        html.append("<nav style='height: auto;text-align: right;'>");
        html.append("<ul class='pagination' id='page2'>");
        if (page.getCurpage() > 1) {
            html.append("<li><a href='javascript:void(0);' onclick='clickPage(").append(getPageUrl(1)).append(")' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
            html.append("<li class='previous'><a href='javascript:void(0);' onclick='clickPage(").append(getPageUrl(page.getCurpage() - 1)).append(")'>上一页</a></li>");
        }
        for (int i = page.getStartBtn(); i <= page.getEndBtn(); i++) {
            if (page.getCurpage() == i) {
                html.append("<li class='active' page='").append(i).append("'><a href='javascript:void(0);'>").append(i).append("</a></li>");
            } else {
                html.append("<li page='").append(i).append("'><a href='javascript:void(0);' onclick='clickPage(").append(getPageUrl(i)).append(")'>").append(i).append("</a></li>");
            }
        }
        if (page.getCurpage() < page.getPagecount()) {
            html.append("<li class='next'><a href='javascript:void(0);' onclick='clickPage(").append(getPageUrl(page.getCurpage() + 1)).append(")'>下一页</a></li>");
            html.append("<li><a href='javascript:void(0);' onclick='clickPage(").append(getPageUrl(page.getPagecount())).append(")' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
        }
        html.append("<li class='next'><a href='javascript:void(0);'>共").append(page.getPagecount()).append("页</a></li>");
        html.append("</ul>");
        html.append("</nav>");
        return html.toString();
    }

    private void initPage() {
        this.page = curPage.get();
        curPage.remove();
    }

    private String getPageUrl(int curpage) {
        Map<String, String> query = page.getQuery();
        query.put("pageNo", String.valueOf(curpage));
        query.put("pageSize", String.valueOf(page.getPagesize()));
        return page.getUri() + "?" + RequestUtil.toQueryString(query);
    }

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        //将模版里的数字参数转化成int类型的方法，，其它类型的转换请看freemarker文档
        // 待学习 如何接受page类型的参数进行处理  先用本地线程绑定解决
        environment.getOut().write(handlePage());
        if (templateDirectiveBody != null) {
            templateDirectiveBody.render(environment.getOut());
        } else {
            throw new RuntimeException("标签内部至少要加一个空格");
        }
    }

}
