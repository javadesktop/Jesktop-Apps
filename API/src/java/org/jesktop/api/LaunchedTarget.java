
/*****************************************************************************
 * Copyright (C) Jesktop Organization. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the jesktop-bsd-license.html file.                                        *
 *****************************************************************************/
package org.jesktop.api;

import org.jesktop.launchable.LaunchableTarget;


/**
 * Class LaunchedTarget
 *
 *
 * @author <a href="mailto:Paul_Hammant@yahoo.com">Paul_Hammant@yahoo.com</a> Jan 2001.
 * @version $Revision: 1.1.1.1 $
 */
public interface LaunchedTarget {

    /**
     * Method getTargetName
     *
     *
     * @return
     *
     */
    String getTargetName();

    /**
     * Method getDisplaytName
     *
     *
     * @return
     *
     */
    String getDisplaytName();

    /**
     * Method getInstantiatedApp
     *
     *
     * @return
     *
     */
    Object getInstantiatedApp();

    /**
     * Method isFromThisLaunchableTarget
     *
     *
     * @param launchableTarget
     *
     * @return
     *
     */
    boolean isFromThisLaunchableTarget(LaunchableTarget launchableTarget);

    /**
     * Method toFront
     *
     *
     */
    void toFront();
}
