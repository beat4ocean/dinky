/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.dinky;

import org.dinky.data.result.ExplainResult;
import org.dinky.data.result.IResult;
import org.dinky.gateway.enums.SavePointType;
import org.dinky.gateway.result.SavePointResult;
import org.dinky.job.Job;
import org.dinky.job.JobConfig;
import org.dinky.job.JobManagerHandler;
import org.dinky.job.JobResult;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobManagerServiceImpl extends UnicastRemoteObject implements ServerExecutorService {

    JobManagerHandler jobManagerHandler;

    public JobManagerServiceImpl() throws RemoteException {}

    @Override
    public void init(JobConfig config, boolean isPlanMode) {
        jobManagerHandler = JobManagerHandler.build(config, isPlanMode);
    }

    @Override
    public boolean close() {
        return jobManagerHandler.close();
    }

    @Override
    public ObjectNode getJarStreamGraphJson(String statement) throws RemoteException {
        return jobManagerHandler.getJarStreamGraphJson(statement);
    }

    @Override
    public JobResult executeJarSql(String statement) throws RemoteException {
        try {
            return jobManagerHandler.executeJarSql(statement);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public JobResult executeSql(String statement) throws RemoteException {
        try {
            return jobManagerHandler.executeSql(statement);
        } catch (Exception ex) {
            log.error("executeSql error", ex);
            return null;
        }
    }

    @Override
    public IResult executeDDL(String statement) throws RemoteException {
        return jobManagerHandler.executeDDL(statement);
    }

    @Override
    public ExplainResult explainSql(String statement) throws RemoteException {
        return jobManagerHandler.explainSql(statement);
    }

    @Override
    public ObjectNode getStreamGraph(String statement) throws RemoteException {
        return jobManagerHandler.getStreamGraph(statement);
    }

    @Override
    public String getJobPlanJson(String statement) throws RemoteException {
        return jobManagerHandler.getJobPlanJson(statement);
    }

    @Override
    public boolean cancelNormal(String jobId) throws RemoteException {
        return jobManagerHandler.cancelNormal(jobId);
    }

    @Override
    public SavePointResult savepoint(String jobId, SavePointType savePointType, String savePoint)
            throws RemoteException {
        return jobManagerHandler.savepoint(jobId, savePointType, savePoint);
    }

    @Override
    public String exportSql(String sql) {
        return jobManagerHandler.exportSql(sql);
    }

    @Override
    public Job getJob() throws RemoteException {
        return jobManagerHandler.getJob();
    }

    @Override
    public void prepare(String statement) throws RemoteException {
        jobManagerHandler.prepare(statement);
    }
}