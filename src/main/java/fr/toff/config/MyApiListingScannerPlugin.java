package fr.toff.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiDescriptionBuilder;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

@Component
public class MyApiListingScannerPlugin implements ApiListingScannerPlugin {

	private final CachingOperationNameGenerator operationNames;
	private final TypeResolver resolver;

	@Autowired
	public MyApiListingScannerPlugin(CachingOperationNameGenerator operationNames, TypeResolver resolver) {
		this.operationNames = operationNames;
		this.resolver = resolver;
	}
	
	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	@Override
	public List<ApiDescription> apply(DocumentationContext context) {
		
		List<ApiDescription> apis = new ArrayList<>();

		apis.add(getItemNameApi(context));
		apis.add(getItemNumberApi(context));
		
		return apis;
	}
	
	private ApiDescription getItemNameApi(DocumentationContext context) {

		Parameter param = new ParameterBuilder()
			.description("Name of the item")
			.type(resolver.resolve(String.class))
			.name("value")
			.parameterType("query")
			.required(true)
			.modelRef(new ModelRef("string"))
			.build();
		
		ResponseMessage respMsg = new ResponseMessageBuilder()
			.code(200)
			.responseModel(new ModelRef("ItemName"))
			.message("Successfully run ItemNameController method")
			.build();

		Operation ope = new OperationBuilder(operationNames)
			.authorizations(new ArrayList<>())
			.method(HttpMethod.GET)
			.codegenMethodNameStem("getItemName")
			.parameters(Arrays.asList(param))
			.consumes(new HashSet<String>(Arrays.asList("application/json")))
			.produces(new HashSet<String>(Arrays.asList("application/json")))
			.responseModel(new ModelRef("ItemName"))
			.responseMessages(new HashSet<ResponseMessage>(Arrays.asList(respMsg)))
			.tags(new HashSet<String>(Arrays.asList("itemController")))
			.build();
		
		ApiDescription apiName = new ApiDescriptionBuilder(context.operationOrdering())
			.path("/item/name")
			.description("Item Name Controller")
			.groupName("itemController")
			.operations(Arrays.asList(ope))
			.hidden(false)
			.build();
		
		return apiName;
	}

	private ApiDescription getItemNumberApi(DocumentationContext context) {

		Parameter param = new ParameterBuilder()
			.description("Number of the item")
			.type(resolver.resolve(Integer.class))
			.name("value")
			.parameterType("query")
			.required(true)
			.modelRef(new ModelRef("int"))
			.build();
		
		ResponseMessage respMsg = new ResponseMessageBuilder()
			.code(200)
			.responseModel(new ModelRef("ItemNumber"))
			.message("Successfully run ItemNumberController method")
			.build();

		Operation ope = new OperationBuilder(operationNames)
			.authorizations(new ArrayList<>())
			.method(HttpMethod.GET)
			.codegenMethodNameStem("getItemNumber")
			.parameters(Arrays.asList(param))
			.consumes(new HashSet<String>(Arrays.asList("application/json")))
			.produces(new HashSet<String>(Arrays.asList("application/json")))
			.responseModel(new ModelRef("ItemNumber"))
			.responseMessages(new HashSet<ResponseMessage>(Arrays.asList(respMsg)))
			.tags(new HashSet<String>(Arrays.asList("itemController")))
			.build();
		
		ApiDescription apiName = new ApiDescriptionBuilder(context.operationOrdering())
			.path("/item/number")
			.description("Item Number Controller")
			.groupName("itemController")
			.operations(Arrays.asList(ope))
			.hidden(false)
			.build();
		
		return apiName;
	}

}
