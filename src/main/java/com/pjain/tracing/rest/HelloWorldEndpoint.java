package com.pjain.tracing.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.pjain.tracing.rest.service.HelloService;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@ApplicationScoped
@Path("/hello")
public class HelloWorldEndpoint {

	@Inject
	private HelloService helloService;
	
	@Inject
	private io.opentracing.Tracer tracer;

	@GET
	@Produces("text/plain")
	public Response doGet() {
		tracer.buildSpan("get_hello").start();
		return Response.ok("Hello lets trace everything! after " + helloService.process() + " ms.").build();
	}
}