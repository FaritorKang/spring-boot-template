package net.unmz.java.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * Project Name:
 * 功能描述：
 *
 * @author Faritor
 * @version 1.0
 * @date 2021-2-2 22:01
 * @since JDK 1.8
 */
@Configuration
public class WebMvcResourceConfig extends WebMvcConfigurationSupport {

    /**
     * 将swagger静态资源注册,不然访问swagger-ui.html页面会404
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 因为继承了WebMvcConfigurationSupport重写了addResourceHandlers,导致配置文件中的时间格式化不生效,所以需要手动重新配置
     *
     * @return
     */
    @Bean
    public ObjectMapper jacksonObjectMapperCustomization() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        format.setTimeZone(timeZone);

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .timeZone(timeZone)
                .dateFormat(format);

        return builder.build();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(c -> c instanceof MappingJackson2HttpMessageConverter);
        converters.add(new MappingJackson2HttpMessageConverter(jacksonObjectMapperCustomization()));
    }


}
