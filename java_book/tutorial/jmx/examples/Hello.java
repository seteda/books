/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/*
 * Hello.java - MBean implementation for the Hello MBean. This class must
 * implement all the Java methods declared in the HelloMBean interface,
 * with the appropriate behavior for each one.
 */

package com.example;

import javax.management.*;

public class Hello
	extends NotificationBroadcasterSupport implements HelloMBean {

    public void sayHello() {
	System.out.println("hello, world");
    }

    public int add(int x, int y) {
	return x + y;
    }

    /* Getter for the Name attribute. The pattern shown here is frequent: the
       getter returns a private field representing the attribute value. In our
       case, the attribute value never changes, but for other attributes it
       might change as the application runs. Consider an attribute representing
       statistics such as uptime or memory usage, for example. Being read-only
       just means that it can't be changed through the management interface. */
    public String getName() {
	return this.name;
    }

    /* Getter for the CacheSize attribute. The pattern shown here is
       frequent: the getter returns a private field representing the
       attribute value, and the setter changes that field. */
    public int getCacheSize() {
	return this.cacheSize;
    }

    /* Setter for the CacheSize attribute. To avoid problems with
       stale values in multithreaded situations, it is a good idea
       for setters to be synchronized. */
    public synchronized void setCacheSize(int size) {
	int oldSize = this.cacheSize;
	this.cacheSize = size;

	/* In a real application, changing the attribute would
	   typically have effects beyond just modifying the cacheSize
	   field.  For example, resizing the cache might mean
	   discarding entries or allocating new ones. The logic for
	   these effects would be here. */
	System.out.println("Cache size now " + this.cacheSize);

	/* Construct a notification that describes the change. The
	   "source" of a notification is the ObjectName of the MBean
	   that emitted it. But an MBean can put a reference to
	   itself ("this") in the source, and the MBean server will
	   replace this with the ObjectName before sending the
	   notification on to its clients.

	   For good measure, we maintain a sequence number for each
	   notification emitted by this MBean.

	   The oldValue and newValue parameters to the constructor are
	   of type Object, so we are relying on Tiger's autoboxing
	   here.  */
	Notification n =
	    new AttributeChangeNotification(this,
					    sequenceNumber++,
					    System.currentTimeMillis(),
					    "CacheSize changed",
					    "CacheSize",
					    "int",
					    oldSize,
					    this.cacheSize);

	/* Now send the notification using the sendNotification method
	   inherited from the parent class NotificationBroadcasterSupport. */
	sendNotification(n);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
	String[] types = new String[] {
	    AttributeChangeNotification.ATTRIBUTE_CHANGE
	};
	String name = AttributeChangeNotification.class.getName();
	String description = "An attribute of this MBean has changed";
	MBeanNotificationInfo info =
	    new MBeanNotificationInfo(types, name, description);
	return new MBeanNotificationInfo[] {info};
    }

    private final String name = "Reginald";
    private int cacheSize = DEFAULT_CACHE_SIZE;
    private static final int DEFAULT_CACHE_SIZE = 200;

    private long sequenceNumber = 1;
}
