package com.frankdevhub.site.service;

import com.frankdevhub.site.core.data.rest.results.Response;
import com.frankdevhub.site.core.utils.SiteMapParseUtils;
import com.frankdevhub.site.core.utils.SpringUtils;
import com.frankdevhub.site.repository.SiteMapPushRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/siteMap")
@SuppressWarnings("all")
public class SiteMapService {

	private final Logger LOG = LoggerFactory.getLogger(SiteMapService.class);

	@Value("${site.hostname}")
	private String DEFAULT_HOSTNAME;

	@Value("${site.port}")
	private String DEFAULT_PORT;

	@Value("${site.domain}")
	private String DEFAULT_DOMIAN;

	private SiteMapPushRecordRepository getRepository() {
		return SpringUtils.getBean(SiteMapPushRecordRepository.class);
	}

	@RequestMapping(value = "/baidu/platform/submit", method = RequestMethod.POST)
	public Response<Map<Object, Object>> submit(@RequestParam("file") MultipartFile file) {
		try {
			LOG.info("using DEFAULT_HOSTNAME : [" + DEFAULT_HOSTNAME + "]");
			LOG.info("using DEFAULT_PORT : [" + DEFAULT_PORT + "]");
			LOG.info("using DEFAULT_DOMIAN : [" + DEFAULT_DOMIAN + "]");

			String fileName = file.getOriginalFilename();
			LOG.info("site map filename = [" + fileName + "]");
			String suffix = fileName.substring(fileName.indexOf(".") + 1);

			if (!suffix.equals("xml")) {
				throw new Exception("sitemap file should be xml format");
			}
			File temp = File.createTempFile("temp" + new Date().getTime(), "xml");
			file.transferTo(temp);
			Map<Object, Object> result = new SiteMapParseUtils(DEFAULT_HOSTNAME, DEFAULT_PORT, DEFAULT_DOMIAN)
					.submitXMLDocument(temp);

			return new Response<Map<Object, Object>>().setData(result).setMsg("success").success();
		} catch (Exception e) {
			e.printStackTrace();

			LOG.error("error", e);
			return new Response<Map<Object, Object>>().setData(null).setMsg(e.getMessage()).failed(e);
		}
	}
}
