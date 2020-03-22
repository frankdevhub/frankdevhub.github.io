package com.frankdevhub.site.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.frankdevhub.site.core.data.rest.requests.PageIpLoggerRequest;
import com.frankdevhub.site.core.data.rest.results.Response;

@CrossOrigin
@RestController
@RequestMapping("/siteMap")
public class SiteMapService {

	private final Logger LOG = LoggerFactory.getLogger(SiteMapService.class);

	@RequestMapping(value = "/baidu/platform/submit", method = RequestMethod.POST)
	public Response<Map<Object, Object>> submit(@Validated @RequestBody PageIpLoggerRequest pageRequest,
			HttpServletRequest request) {
		try {

			return new Response<Map<Object, Object>>().setData(null).setMsg("success").success();
		} catch (Exception e) {
			e.printStackTrace();

			LOG.error("error", e);
			return new Response<Map<Object, Object>>().setData(null).setMsg(e.getMessage()).failed(e);
		}
	}
}
