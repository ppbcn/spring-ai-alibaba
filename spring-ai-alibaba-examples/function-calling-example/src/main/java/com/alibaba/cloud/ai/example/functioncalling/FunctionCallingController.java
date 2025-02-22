/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.example.functioncalling;

import com.alibaba.cloud.ai.example.functioncalling.function.MockWeatherService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/func")
public class FunctionCallingController {

    private final ChatClient chatClient;

    public FunctionCallingController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/weather-service")
    public String weatherService(String subject) {
        return chatClient.prompt()
                .function("getWeather", "根据城市查询天气", new MockWeatherService())
                .user(subject)
                .call()
                .content();
    }

	@GetMapping("/order-detail")
	public String orderDetail() {
		return chatClient.prompt()
			.functions("getOrderFunction")
			.user("帮我查询一下订单, 用户编号为1001, 订单编号为2001")
			.call()
			.content();
	}

	@GetMapping("/baidu-search")
	public String baiduSearch(@RequestParam String query) {
		return chatClient.prompt()
			.functions("baiduSearchService")
			.user(query)
			.call()
			.content();
	}
	
	@GetMapping("/bing-search")
	public String bingSearch(@RequestParam String query) {
		return chatClient.prompt()
				.functions("bingSearchService")
				.user(query)
				.call()
				.content();
	}

    @GetMapping("/getTime")
    public String getTime(String text) {
        return chatClient.prompt()
                .functions("getCityTime")
                .user(text)
                .call()
                .content();
    }

    @GetMapping("/dingTalk-custom-robot-send")
    public String dingTalkCustomRobotSend(String input) {
        return chatClient.prompt()
                .functions("dingTalkGroupSendMessageByCustomRobotFunction")
                .user(String.format("帮我用自定义机器人发送'%s'", input))
                .call()
                .content();
    }

	@GetMapping("/gaode-get-address-weather")
	public String gaoDeGetAddressWeatherFunction(String input) {
		return chatClient.prompt()
				.functions("gaoDeGetAddressWeatherFunction")
				.system("如果用户输入的内容中想询问天气情况,而且还给定了地址,则使用工具获取天气情况,不然提示用户缺少信息")
				.user(input)
				.call()
				.content();
	}


	@GetMapping("/getWeather")
	public String getWeather(@RequestParam String text) {
		return chatClient.prompt()
				.functions("getWeatherService")
				.user(text)
				.call()
				.content();
	}

	@GetMapping("/translate")
	public String translate(@RequestParam String text) {
		return chatClient.prompt()
				.functions("translateService")
				.user(text)
				.call()
				.content();
	}

}
