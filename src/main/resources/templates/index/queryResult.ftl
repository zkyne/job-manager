<table class="table table-hover">
    <thead>
    <tr>
        <th data-class="expand">路径URL</th>
        <th data-class="expand">执行规则</th>
        <th data-class="expand">任务状态</th>
        <th data-class="expand">任务描述</th>
        <th data-class="expand">操作</th>
    </tr>
    </thead>
    <tbody id="queryResult">
        <#if crontabs?? && (crontabs?size > 0) >
            <#list crontabs as crontab>
            <tr <#if crontab.status == 0>class="success"</#if> id="curTr">
                <td id="url">${crontab.url}</td>
                <td id="cronExp">${crontab.cronExp}</td>
                <td id="status" data-value="${crontab.status}"><#if crontab.status == 0>启用中<#else>已停止</#if></td>
                <td id="descript">${crontab.descript}</td>
                <td>
                    <button type="button" onclick="crontab._initUpdCrontab(this,'${crontab.jobId}')" class="btn btn-info">修 改</button>&nbsp;
                    <#if crontab.status == 0>
                        <button type="button" onclick="crontab._reverseCrontab(this,'${crontab.jobId}');" class="btn btn-warning">停 止</button>&nbsp;
                    <#else>
                        <button type="button" onclick="crontab._reverseCrontab(this,'${crontab.jobId}');" class="btn btn-success">启 用</button>&nbsp;
                    </#if>
                    <button type="button" onclick="crontab._delCrontab(this,'${crontab.jobId}');" class="btn btn-danger">删 除</button>&nbsp;
                </td>
            </tr>
            </#list>
        </#if>
    </tbody>
</table>
<#if page??>
<nav style="height: auto;text-align: right;" aria-label="Search result pages">${page}</nav>
</#if>



