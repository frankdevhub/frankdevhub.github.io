package com.frankdevhub.site.service;

import com.frankdevhub.site.core.data.rest.results.Response;
import com.frankdevhub.site.core.generators.snowflake.SnowflakeGenerator;
import com.frankdevhub.site.core.utils.HostUtils;
import com.frankdevhub.site.core.utils.SpringUtils;
import com.frankdevhub.site.core.utils.TencentIpLocator;
import com.frankdevhub.site.entities.PageLoggerIpEntity;
import com.frankdevhub.site.repository.PageLoggerIpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.Assert;

import java.util.Date;


//*****************************GET ADDRESS FROM LAT LNG**********************************************************

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
*/


@RestController(value = "/logger")
public class PageLoggerService {

    private final Logger LOG = LoggerFactory.getLogger(PageLoggerService.class);

    private final PageLoggerIpRepository getRepository() {
        return SpringUtils.getBean(PageLoggerIpRepository.class);
    }

    @RequestMapping(value = "/page/ip", method = RequestMethod.POST)
    public Response<Boolean> recordPageIpLogger(@RequestBody String url) {
        try {
            LOG.info("record page logger :" + url);
            String ip = HostUtils.getMyIp();
            Assert.notNull(ip, "ip object cannot found");
            String _location[] = TencentIpLocator.getIpLocation(ip);
            String location = _location[0] + "," + _location[1];

            LOG.debug("location value: " + location);
            Long id = new SnowflakeGenerator().generateKey();
            PageLoggerIpEntity entity = new PageLoggerIpEntity();
            entity.setId(id).setLogId(id)
                    .setLatitude(_location[0])
                    .setLongitude(_location[1])
                    .setDate(new Date().getTime())
                    .setUrl(url)
                    .setIpAddress(ip);

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
