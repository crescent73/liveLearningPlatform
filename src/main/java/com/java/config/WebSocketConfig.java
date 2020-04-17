package com.java.config;

import com.java.filter.HandShakeInterceptor;
import com.java.filter.MyChannelInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    //拦截器注入service失败解决办法
    @Bean
    public MyChannelInterceptor myChannelInterceptor(){
        return new MyChannelInterceptor();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //添加访问域名限制可以防止跨域soket连接
        //setAllowedOrigins("http://localhost:8085")
        registry.addEndpoint("/live").setAllowedOrigins("*").addInterceptors(new HandShakeInterceptor()).withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*.enableSimpleBroker("/topic","/queue");*/
        //假如需要第三方消息队列，比如rabitMQ,activeMq，在这里配置//61613
        registry.setApplicationDestinationPrefixes("/demo")
                .enableStompBrokerRelay("/topic","/queue")
                .setRelayHost("106.15.251.188")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setSystemLogin("guest")
                .setSystemPasscode("guest")
                .setSystemHeartbeatSendInterval(5000)
                .setSystemHeartbeatReceiveInterval(4000);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(myChannelInterceptor());
//        super.configureClientInboundChannel(registration);
    }
}
