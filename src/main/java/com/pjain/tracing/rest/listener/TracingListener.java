package com.pjain.tracing.rest.listener;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.uber.jaeger.Configuration;
import com.uber.jaeger.Tracer;
import com.uber.jaeger.samplers.ProbabilisticSampler;

import io.opentracing.util.GlobalTracer;

@WebListener
public class TracingListener implements ServletContextListener {

	@Inject
	private io.opentracing.Tracer tracer;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		GlobalTracer.register(tracer);
	}

	/**
	 * To use another tracer like zipkin, just update this method.
	 */
	@Produces
	@Singleton
	public static io.opentracing.Tracer jaegerTracer() {
		// return new Tracer.Builder("wildfly-swarm", null, new
		// ProbabilisticSampler(1d)).build();
		return new Configuration("wildfly-swarm-1",
				new Configuration.SamplerConfiguration(ProbabilisticSampler.TYPE, 1),
				new Configuration.ReporterConfiguration()).getTracer();
	}

}
