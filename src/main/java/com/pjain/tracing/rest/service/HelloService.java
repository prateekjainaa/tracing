package com.pjain.tracing.rest.service;

import java.util.Random;

import javax.inject.Inject;

import io.opentracing.Span;

public class HelloService {

	@Inject
	private io.opentracing.Tracer tracer;
	
	public int process() {
		Span span = tracer.buildSpan("helloService.tracer").start();
		span.setBaggageItem("method", "process");
		System.out.println("Inside hello service.");
		int i= -1;
		try {
			privateProcess();
			i = new Random().nextInt(200);
			if (i > 125) {
				throw new RuntimeException("Too much delay added.");
			}
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	private void privateProcess() {
		Span s = tracer.buildSpan("privateProcess").asChildOf(tracer.activeSpan()).start();
		s.setTag("coolTag", "data_of_tag");
		s.setBaggageItem("method", "privateProcess");
		s.log("This is a custom log message.");
	}
}
