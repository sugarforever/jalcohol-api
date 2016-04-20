<html>
<body>
    <div>
        <#if Session.codoonToken?? >
            <span>欢迎</span>
        <#else >
            <a href="/authorize/?platform=codoon">通过咕咚登录授权</a>
        </#if>
    </div>
</body>
</html>