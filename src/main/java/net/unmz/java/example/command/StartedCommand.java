package net.unmz.java.example.command;

import lombok.extern.slf4j.Slf4j;
import net.unmz.java.example.util.SpringBeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Project Name:
 * 功能描述：
 *
 * @author faritor
 * @version 1.0
 * @date 2021-3-10 0:16
 * @since JDK 1.8
 */
@Slf4j
@Component
public class StartedCommand implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Environment environment = SpringBeanUtils.getBean(Environment.class);
        log.info("=========================================================================================");
        log.info("========================== {}项目{}环境启动成功! 访问链接：http://localhost:{}{} ===========",
                environment.getProperty("spring.application.name", "项目未配置应用明,请配置spring.application.name"),
                environment.getActiveProfiles(),
                environment.getProperty("server.port", ""),
                environment.getProperty("server.servlet.context-path", ""));
        log.info("=========================================================================================");
    }

}
