package com.jalcoholapi.mvc.c;

import com.jalcoholapi.mvc.m.CodoonNotificationForm;
import com.jalcoholapi.mvc.m.CodoonRoute;
import com.jalcoholapi.mvc.m.Rank;
import com.jalcoholapi.persistence.model.CodoonNotification;
import com.jalcoholapi.persistence.model.UserToken;
import com.jalcoholapi.service.CodoonCrawlService;
import com.jalcoholapi.service.CodoonNotificationService;
import com.jalcoholapi.service.CodoonService;
import com.jalcoholapi.service.UserTokenService;
import com.mashape.unirest.http.JsonNode;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by weiliyang on 7/24/15.
 */
@ConfigurationProperties
@RestController
@RequestMapping("/api/codoon/")
public class CodoonRestfulController {

    private final static Logger logger = LoggerFactory.getLogger(CodoonRestfulController.class);

    @Autowired
    private CodoonService codoonService;
    @Autowired
    private CodoonNotificationService codoonNotificationService;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private CodoonCrawlService codoonCrawlService;

    @RequestMapping(value = "/token")
    public Object getToken(HttpSession session) {
        return session.getAttribute("userToken");
    }

    @RequestMapping(value = "/call", method = RequestMethod.POST)
    public Object postCall(@RequestParam String method, @RequestParam String api, @RequestBody(required = false) String data, @RequestParam String nekot) throws Exception {
        JsonNode responseJsonNode = codoonService.callAPI(method, api, data, nekot, JsonNode.class);
        return responseJsonNode == null ? "" : responseJsonNode.toString();
    }

    @RequestMapping(value = "/call", method = RequestMethod.GET)
    public Object getCall(@RequestParam String method, @RequestParam String api, @RequestParam String nekot) throws Exception {
        JsonNode responseJsonNode = codoonService.callAPI(method, api, null, nekot, JsonNode.class);
        return responseJsonNode == null ? "" : responseJsonNode.toString();
    }

    @RequestMapping(value = "/refresh-token/{refreshToken}/")
    public Object refreshToken(@PathVariable String refreshToken, HttpSession session) throws Exception {
        UserToken token = codoonService.refreshToken(refreshToken);

        if (token == null) {
            session.removeAttribute("codoonToken");
        } else {
            session.setAttribute("codoonToken", token);
        }

        return token;
    }

    @RequestMapping(value = "/notify/", method = RequestMethod.POST)
    public Object notify(@ModelAttribute CodoonNotificationForm codoonNotificationForm) {
        logger.debug("Codoon notification received: " + codoonNotificationForm.toString());
        if (codoonNotificationForm.getStart_time() == null)
            codoonNotificationForm.setStart_time("");
        if (codoonNotificationForm.getEnd_time() == null)
            codoonNotificationForm.setEnd_time("");
        CodoonNotification notification = codoonNotificationService.save(codoonNotificationForm);
        return notification;
    }

    @RequestMapping(value = "/routes/{userId}", method = RequestMethod.GET)
    public Object getRoutes(@PathVariable String userId) {
        UserToken userToken = userTokenService.findByUserId(userId);
        final UserToken refreshedToken = codoonService.refreshToken(userToken.getRefreshToken());
        List routes = null;
        if (refreshedToken != null) {
            routes = codoonNotificationService.findAllCodoonNotificationsByUserId(refreshedToken.getUserId())
                    .parallelStream()
                    .map(codoonNotification -> codoonService.callAPI("post", "get_route_by_id", String.format("{\"route_id\": \"%s\"}", codoonNotification.getResourceId()), userToken.getAccessToken(), CodoonRoute.class))
                    .collect(Collectors.toList());
        }

        return routes;
    }

    @RequestMapping(value = "/user-tokens/", method = RequestMethod.GET)
    public Object getUserTokens() {
        return userTokenService.findAll();
    }

    @RequestMapping(value = "/crawler/login/")
    public Object crawlerLogin(@RequestParam String userName, @RequestParam String password) {
        boolean succeeded = codoonCrawlService.login(userName, password);
        return String.format("{\"succeeded\": %s}", String.valueOf(succeeded));
    }

    @RequestMapping(value = "/crawler/personal/")
    public Object crawlerPersonal(@RequestParam String userName, @RequestParam String password) {
        String url = "http://www.codoon.com/data_v/get_user_statistic";
        Object json = null;
        if (codoonCrawlService.login(userName, password)) {
            json = codoonCrawlService.crawl(url, "GET", null);
        }

        return json;
    }

    private static final String SHEET_NAME = "个人成绩排名";

    @RequestMapping(value = "/crawler/export/")
    public Object crawlerExport(@RequestParam String url, @RequestParam String userName, @RequestParam String password) {
        byte[] bytes = null;
        if (codoonCrawlService.login(userName, password)) {
            bytes = codoonCrawlService.crawl(url, "GET", null);
        }

        List<Rank> ranks = new ArrayList<Rank>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new ByteArrayInputStream(bytes));
            HSSFSheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet != null) {
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); ++i) {
                    HSSFRow row = sheet.getRow(i);

                    Rank rank = new Rank();
                    rank.rank = (int) row.getCell(0).getNumericCellValue();
                    rank.name = row.getCell(1).getStringCellValue();
                    rank.nick = row.getCell(2).getStringCellValue();
                    rank.department = row.getCell(3).getStringCellValue();
                    rank.steps = (long) row.getCell(4).getNumericCellValue();

                    ranks.add(rank);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ranks;
    }
}
