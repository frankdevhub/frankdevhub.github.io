package com.frankdevhub.site.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frankdevhub.site.configuration.CommonInterceptor;
import com.frankdevhub.site.core.data.rest.requests.PageIpLoggerRequest;
import com.frankdevhub.site.core.data.rest.results.Response;
import com.frankdevhub.site.core.generators.snowflake.SnowflakeGenerator;
import com.frankdevhub.site.core.utils.SpringUtils;
import com.frankdevhub.site.core.utils.TencentIpLocator;
import com.frankdevhub.site.entities.PageLoggerIpEntity;
import com.frankdevhub.site.repository.PageLoggerIpRepository;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.util.Assert;

//*************************************GET ADDRESS FROM LAT LNG**********************************************************

/*response status:[HTTP/1.1 200 OK]
content-length:[-1]
response context:[{
    "status": 0,
    "message": "query ok",
    "request_id": "a6dd602c-5fad-11ea-85ed-28c13c908b77",
    "result": {
        "location": {
            "lat": 39.90469,
            "lng": 116.40717
        },
        "address": "北京市东城区正义路2号",
        "formatted_addresses": {
            "recommend": "北京市政府(旧址)",
            "rough": "北京市政府(旧址)"
        },
        "address_component": {
            "nation": "中国",
            "province": "北京市",
            "city": "北京市",
            "district": "东城区",
            "street": "正义路",
            "street_number": "正义路2号"
        },
        "ad_info": {
            "nation_code": "156",
            "adcode": "110101",
            "city_code": "156110000",
            "name": "中国,北京市,北京市,东城区",
            "location": {
                "lat": 39.916668,
                "lng": 116.434578
            },
            "nation": "中国",
            "province": "北京市",
            "city": "北京市",
            "district": "东城区"
        },
        "address_reference": {
            "crossroad": {
                "id": "645479",
                "title": "正义路/东交民巷(路口)",
                "location": {
                    "lat": 39.902458,
                    "lng": 116.407051
                },
                "_distance": 243.1,
                "_dir_desc": "北"
            },
            "town": {
                "id": "110101001",
                "title": "东华门街道",
                "location": {
                    "lat": 39.923271,
                    "lng": 116.396828
                },
                "_distance": 0,
                "_dir_desc": "内"
            },
            "street_number": {
                "id": "1355963025687984233",
                "title": "正义路2号",
                "location": {
                    "lat": 39.904514,
                    "lng": 116.407249
                },
                "_distance": 0,
                "_dir_desc": ""
            },
            "street": {
                "id": "4447337210863392603",
                "title": "正义路",
                "location": {
                    "lat": 39.903751,
                    "lng": 116.407097
                },
                "_distance": 2.7,
                "_dir_desc": ""
            },
            "landmark_l2": {
                "id": "1355963025687984233",
                "title": "北京市政府(旧址)",
                "location": {
                    "lat": 39.904518,
                    "lng": 116.407249
                },
                "_distance": 0,
                "_dir_desc": "内"
            }
        }
    }
}]
21:23:22.235 [main] INFO com.frankdevhub.site.core.utils.TencentIpLocator - address:[北京市东城区正义路2号]
北京市东城区正义路2号
************************************************************************************************************************/

@CrossOrigin
@RestController
@RequestMapping("/logger")
public class PageLoggerService {

	private final Logger LOG = LoggerFactory.getLogger(PageLoggerService.class);

	private final PageLoggerIpRepository getRepository() {
		return SpringUtils.getBean(PageLoggerIpRepository.class);
	}

	@RequestMapping(value = "/page/ip/ipaddress", method = RequestMethod.GET)
	public Response<PageInfo<PageLoggerIpEntity>> getPageLoggerByClientIp(
			@Validated @RequestParam(name = "ip") String ipAddress) {
		try {

			return new Response<PageInfo<PageLoggerIpEntity>>().setData(null).setMsg("success").success();
		} catch (Exception e) {

			LOG.error("error", e);
			e.printStackTrace();
			return new Response<PageInfo<PageLoggerIpEntity>>().setData(null).setMsg(e.getMessage()).failed(e);
		}
	}

	@RequestMapping(value = "/page/ip/datetime", method = RequestMethod.GET)
	public Response<PageInfo<PageLoggerIpEntity>> getPageLoggerByDateTime(
			@RequestParam(name = "startDateTime", required = true) Long startDateTime,
			@RequestParam(name = "endDateTime", required = true) Long endDateTime,
			@RequestParam(name = "asend", defaultValue = "false") Boolean asend) {
		try {
			List<PageLoggerIpEntity> records;
			records = getRepository().selectByExample(startDateTime, endDateTime, asend);

			return new Response<PageInfo<PageLoggerIpEntity>>().setData(new PageInfo<>(records)).setMsg("success")
					.success();
		} catch (Exception e) {

			LOG.error("error", e);
			e.printStackTrace();
			return new Response<PageInfo<PageLoggerIpEntity>>().setData(null).setMsg(e.getMessage()).failed(e);
		}
	}

	@RequestMapping(value = "/page/ip", method = RequestMethod.POST)
	public Response<Boolean> recordPageIpLogger(@Validated @RequestBody PageIpLoggerRequest pageRequest,
			HttpServletRequest request) {
		try {
			String url = pageRequest.getUrl();
			Assert.notNull(url, "page request url cannot found");

			LOG.info("record page logger :" + url);
			String ip = CommonInterceptor.getRealIp(request);
			Assert.notNull(ip, "ip object cannot found");

			String _location[] = TencentIpLocator.getIpLocation(ip);
			String location = _location[0] + "," + _location[1];

			String macAddress = CommonInterceptor.getMacAddress(ip);
			LOG.info("user mac address: " + macAddress);

			LOG.debug("location value: " + location);
			Long id = new SnowflakeGenerator().generateKey();
			PageLoggerIpEntity entity = new PageLoggerIpEntity();
			entity.setId(id).setLogId(id).setLatitude(_location[0]).setLongitude(_location[1])
					.setDate(new Date().getTime()).setUrl(url).setIpAddress(ip);

			String address = TencentIpLocator.getAddress(location);
			entity.setAddress(address);
			getRepository().insertSelective(entity);

			LOG.info("record page logger complete");
			return new Response<Boolean>().setData(Boolean.TRUE).setMsg("page ip logger restore success").success();
		} catch (Exception e) {
			e.printStackTrace();

			LOG.error("error", e);
			return new Response<Boolean>().setData(null).setMsg(e.getMessage()).failed(e);
		}
	}

}
