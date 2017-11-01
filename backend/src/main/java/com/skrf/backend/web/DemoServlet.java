package com.skrf.backend.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.edmx.EdmxReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skrf.backend.odatamodel.events_settings;
import com.skrf.backend.service.SKRFEdmProvider;
import com.skrf.backend.service.SKRFEntityCollectionProcessor;
import com.skrf.backend.service.SKRFEntityProcessor;
import com.skrf.backend.service.SKRFPrimitiveProcessor;

public class DemoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(DemoServlet.class);

	protected void service(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			
			HttpSession session = req.getSession(true);
			events_settings storage = (events_settings) session.getAttribute(events_settings.class.getName());
			if (storage == null) {
				storage = new events_settings();
				session.setAttribute(events_settings.class.getName(), storage);
			} 
			
			// create odata handler and configure it with CsdlEdmProvider and Processor
			OData odata = OData.newInstance();
			// ServiceMetadata edm = odata.createServiceMetadata(new DemoEdmProvider(), new
			// ArrayList<EdmxReference>());
			ServiceMetadata edm = odata.createServiceMetadata(new SKRFEdmProvider(), new ArrayList<EdmxReference>());
			ODataHttpHandler handler = odata.createHandler(edm);
			handler.register(new SKRFEntityCollectionProcessor(storage));
			handler.register(new SKRFEntityProcessor(storage));
			handler.register(new SKRFPrimitiveProcessor(storage));

			// let the handler do the work
			handler.process(req, resp);
		} catch (RuntimeException e) {
			LOG.error("Server Error occurred in ExampleServlet", e);
			throw new ServletException(e);
		}
	}
}
