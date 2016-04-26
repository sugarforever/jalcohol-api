<html>
<body>
    <div>
        <#if Session.userToken?? >
            <span>Hello, ${Session.userToken.userNick}</span>

            <div class="tracker-summary">
                    <#list Request.codoonTrackers as tracker>
                        <div class="tracker">
                            <div class="date field">${tracker.date}</div>
                            <div class="activity field">${tracker.activity}</div>
                            <div class="minutes field">${tracker.minutes}</div>
                            <div class="calories field">${tracker.calories}</div>
                            <div class="steps field">${tracker.steps}</div>
                            <div class="meters field">${tracker.meters}</div>
                        </div>
                    </#list>
            </div>
        <#else >
            <a href="/authorize/?platform=codoon">通过咕咚登录授权</a>
        </#if>
    </div>
</body>
</html>