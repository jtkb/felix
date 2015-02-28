/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.felix.dependencymanager.samples.device.api;

import java.util.Hashtable;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;

public class DeviceAccessImpl implements DeviceAccess {
    volatile Device device;
    volatile DeviceParameter deviceParameter;

    void init(Component c) {
        // Dynamically add an extra dependency on a DeviceParameter.
        DependencyManager dm = c.getDependencyManager();
        c.add(dm.createServiceDependency().setService(DeviceParameter.class, "(device.id=" + device.getDeviceId() + ")").setRequired(
            true));
    }

    void start(Component c) {
        // Our service is starting: before being registered in the OSGi service registry,
        // add here a service property, using the device.id.
        Hashtable<String, Object> props = new Hashtable<>();
        props.put("device.access.id", device.getDeviceId());
        c.setServiceProperties(props);
    }

    @Override
    public Device getDevice() {
        return device;
    }

    @Override
    public DeviceParameter getDeviceParameter() {
        return deviceParameter;
    }
}
