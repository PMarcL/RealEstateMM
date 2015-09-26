package org.RealEstateMM.jersey;

import org.RealEstateMM.context.DevelopmentContext;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main {

	public static void main(String[] args) throws Exception {
		new DevelopmentContext().apply();
		new Main().startServer();
	}

	private void startServer() throws Exception {
		final int PORT_SERVER = 8080;

		Server server = new Server(PORT_SERVER);
		ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
		configureJersey(servletContextHandler);
		server.start();
		server.join();
	}

	private void configureJersey(ServletContextHandler servletContextHandler) {
		ServletContainer container = new ServletContainer(setUpRessoures());
		ServletHolder jerseyServletHolder = new ServletHolder(container);
		servletContextHandler.addServlet(jerseyServletHolder, "/*");
	}

	private ResourceConfig setUpRessoures() {
		ResourceConfig resourceConfig = new ResourceConfig();

		resourceConfig.packages("org.RealEstateMM.jersey");
		resourceConfig.register(JacksonFeature.class);

		return resourceConfig;
	}

}
