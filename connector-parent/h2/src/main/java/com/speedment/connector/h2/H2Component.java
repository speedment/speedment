/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
 */
package com.speedment.connector.h2;

import com.speedment.common.injector.annotation.InjectKey;

/**
 * A component required to communicate with H2 databases.
 * <p>
 * 
 * @author  Per Minborg
 * @since   1.0.0
 */
@InjectKey(H2Component.class)
public interface H2Component  {};