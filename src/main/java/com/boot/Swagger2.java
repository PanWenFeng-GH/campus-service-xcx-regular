package com.boot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Parameter;

@Configuration
@EnableSwagger2
public class Swagger2 {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo()).groupName("小程序api")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.boot.api"))
				.paths(PathSelectors.any()).build().globalOperationParameters(this.globalOperation());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("小程序api")
				.termsOfServiceUrl("http://127.0.0.1:8080/")
				.contact("图图")
				.version("1.0").build();
	}
	
	private List<Parameter> globalOperation(){
        //添加head参数配置start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        //第一个token为传参的key，第二个token为swagger页面显示的值
        tokenPar.name("token").description("口令")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .required(false).build();
        pars.add(tokenPar.build());

        return pars;
    }

}