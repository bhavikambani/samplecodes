/*
 * @(#)TimerListener
 *
 * Copyright (c) 1998 Karl Moss. All Rights Reserved.
 *
 * You may study, use, modify, and distribute this software for any
 * purpose provided that this copyright notice appears in all copies.
 *
 * This software is provided WITHOUT WARRANTY either expressed or
 * implied.
 *
 * @author  Karl Moss
 * @version 1.0
 * @date    11Mar98
 *
 */

package com.elitecore.jdbchelper.timer;

/**
  * <p>This interface is implemented by classes that wish to
  * receive timer events. The Timer class will invoke the
  * TimerEvent method for every time interval specified when
  * the Timer is started. This gives the implementing class
  * an opportunity to perform some type of time-dependent
  * checking.
  */

public interface TimerListener
{
  /**
    * <p>Called for each timer clock cycle
    */
  void TimerEvent(Object object);
}
