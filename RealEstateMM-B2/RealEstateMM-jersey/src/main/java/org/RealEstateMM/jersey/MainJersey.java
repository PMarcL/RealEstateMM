package org.RealEstateMM.jersey;

import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Server;

public class MainJersey {

	public static void main(String[] args) throws Exception {
		new MainJersey().startServer();
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
		ServletContainer container = new ServletContainer(new ResourceConfig().packages("org.RealEstateMM.jersey")
				.register(JacksonFeature.class));
		ServletHolder jerseyServletHolder = new ServletHolder(container);
		servletContextHandler.addServlet(jerseyServletHolder, "/*");
	}

}
