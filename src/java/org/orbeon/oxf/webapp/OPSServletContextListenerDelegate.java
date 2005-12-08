/**
 *  Copyright (C) 2004 Orbeon, Inc.
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU Lesser General Public License as published by the Free Software Foundation; either version
 *  2.1 of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  The full text of the license is available at http://www.gnu.org/copyleft/lesser.html
 */
package org.orbeon.oxf.webapp;

import org.apache.log4j.Logger;
import org.orbeon.oxf.common.OXFException;
import org.orbeon.oxf.pipeline.InitUtils;
import org.orbeon.oxf.util.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This listener listens for HTTP context lifecycle changes.
 */
public class OPSServletContextListenerDelegate implements ServletContextListener {

    private static Logger logger = LoggerFactory.createLogger(OPSServletContextListener.class);

    public static final String INIT_PROCESSOR_PROPERTY_PREFIX = "oxf.context-initialized-processor.";
    public static final String INIT_PROCESSOR_INPUT_PROPERTY = "oxf.context-initialized-processor.input.";
    public static final String DESTROY_PROCESSOR_PROPERTY_PREFIX = "oxf.context-destroyed-processor.";
    public static final String DESTROY_PROCESSOR_INPUT_PROPERTY = "oxf.context-destroyed-processor.input.";

    public void contextInitialized(ServletContextEvent event) {
        try {
            InitUtils.run(event.getServletContext(), null, INIT_PROCESSOR_PROPERTY_PREFIX, INIT_PROCESSOR_INPUT_PROPERTY);
        } catch (Exception e) {
            logger.error("Exception when running Servlet context initialization processor", OXFException.getRootThrowable(e));
            throw new OXFException(e);
        }
    }
    
    public void contextDestroyed(ServletContextEvent event) {
        try {
            InitUtils.run(event.getServletContext(), null, DESTROY_PROCESSOR_PROPERTY_PREFIX, DESTROY_PROCESSOR_INPUT_PROPERTY);
        } catch (Exception e) {
            logger.error("Exception when running Servlet context destruction processor", OXFException.getRootThrowable(e));
            throw new OXFException(e);
        }
    }
}
