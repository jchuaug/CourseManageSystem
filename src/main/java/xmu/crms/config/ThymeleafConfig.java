package xmu.crms.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
/**
 * 
* <p>Title: ThymeleafConfig.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2018<／p>
 * @author Jackey
 * @date 2018年1月3日
 */
@Configuration
public class ThymeleafConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public ViewResolver viewResolver() {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine((ISpringTemplateEngine) templateEngine());
    resolver.setCharacterEncoding("UTF-8");
    return resolver;
  }

  @Bean
  public TemplateEngine templateEngine() {
    SpringTemplateEngine engine = new SpringTemplateEngine();
    engine.setEnableSpringELCompiler(true);
    engine.setTemplateResolver(templateResolver());
    return engine;
  }

  private ITemplateResolver templateResolver() {
    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setApplicationContext(applicationContext);
    resolver.setPrefix("classpath:/templates/");
    resolver.setTemplateMode(TemplateMode.HTML);
    return resolver;
  }
}