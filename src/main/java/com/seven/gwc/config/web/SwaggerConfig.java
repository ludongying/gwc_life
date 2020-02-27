package com.seven.gwc.config.web;

import com.seven.gwc.config.constant.JwtConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: GD
 * @Description:
 * @Date: 2020/2/25 13:59
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "tonfun", name = "swagger-open", havingValue = "true")
public class SwaggerConfig extends WebMvcConfigurerAdapter {


    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(JwtConstants.AUTH_HEADER).description("token").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        return pars;
    }

    @Bean
    public Docket webApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .globalOperationParameters(setHeaderToken())
                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.seven.gwc.modular.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("渔政船接口平台")
                .description("所有的应用程序都可以通过JSON访问对象模型数据.")
                .termsOfServiceUrl("无服务条款")
                .contact("翟增来（ZZL）、张立华（ZLH）、申豪乾（SHQ）")
                .version("1.0")
                .build();
    }

}
