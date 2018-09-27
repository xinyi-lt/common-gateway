package com.sxzq.lt.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.sxzq.lt.bean.RestResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

/**
 * 验证码
 * @date: 2018-09-18
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {
	private static final Logger log = LoggerFactory.getLogger(CaptchaController.class);

	@Autowired
	private Producer producer;

	/**
	 * 获取验证码
	 * @date 2018-09-18
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GetMapping(value = "/captchaImage")
	public Mono<Void> getCaptchaImage(WebSession session, ServerHttpResponse response) throws Exception {

		ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
		response.getHeaders().set(HttpHeaders.EXPIRES, "0");
		response.getHeaders().set(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate");
		response.getHeaders().set(HttpHeaders.CACHE_CONTROL, "post-check=0, pre-check=0");
		response.getHeaders().set(HttpHeaders.PRAGMA, "no-cache");
		response.getHeaders().setContentType(MediaType.IMAGE_PNG);

		String capText = producer.createText();
		session.getAttributes().put(Constants.KAPTCHA_SESSION_CONFIG_KEY, capText);
		log.info("sessionId = {} 获取验证码为:{}", session.getId(), capText);

		BufferedImage bi = producer.createImage(capText);

		File file = new File("kaptcha.jpg");
		ImageIO.write(bi, "jpg", file);
		return zeroCopyResponse.writeWith(file, 0, file.length());
	}
	
	/**
	 * 判断验证码是否正确
	 * @author chenlei
	 * @date 2018-09-18
	 * @param captcha
	 * @param request
	 * @return
	 */
	@PostMapping("/captchaCheck")
	@ResponseBody
	public RestResult<Boolean> captchaCheck(@RequestBody Map<String, Object> reqMap, HttpServletRequest request){
		log.info("sessionId = {} 验证验证码:{}", request.getSession().getId(), JSON.toJSONString(reqMap));
		RestResult<Boolean> restResult = new RestResult<>();
		restResult.setData(Boolean.FALSE);
		String captcha = (String) reqMap.get("captcha");
		String capText = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY);
		if (!StringUtils.isEmpty(captcha) && captcha.equalsIgnoreCase(capText)) {
			restResult.setData(Boolean.TRUE);
		} else {
			request.getSession().invalidate();
		}
		return restResult;
	}

}
