package com.java.config;

import com.java.filter.HandShakeInterceptor;
import com.java.filter.MyChannelInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    //拦截器注入service失败解决办法
    @Bean
    public MyChannelInterceptor myChannelInterceptor(){
        return new MyChannelInterceptor();
    }

    /**
     * 添加一个服务器端点，用来接收客户端的连接
     * addEndpoint("/live")表示添加了一个/live端点，客户端可以通过这个端点进行连接
     * withSockJS()的作用是开启SocketJS支持
     * @param registry
     */
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
        registry.setApplicationDestinationPrefixes("/demo")// 配置前缀，有这些前缀的会被 有@SubscribeMapping和@MessageMapping的业务方法拦截
                .enableStompBrokerRelay("/topic","/queue")// 配置前缀，有这些前缀的路由会到broker
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
//        registration.setInterceptors(myChannelInterceptor());
        registration.interceptors(myChannelInterceptor());
//        super.configureClientInboundChannel(registration);
    }
}
