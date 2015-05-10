/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.tinypc.impl;

import java.io.IOException;

import org.tinygroup.rmi.impl.RmiServerImpl;
import org.tinygroup.tinypc.WorkQueue;

/**
 * Created by luoguo on 14-1-23.
 */
public class JobCenterRemote extends AbstractJobCenter {

    public JobCenterRemote(String hostName, int port,String remoteHostName,int remotePort) throws IOException {
        setRmiServer(new RmiServerImpl(hostName, port,remoteHostName,remotePort));
        setWorkQueue((WorkQueue) getRmiServer().getObject("WorkQueue"));
    }
}