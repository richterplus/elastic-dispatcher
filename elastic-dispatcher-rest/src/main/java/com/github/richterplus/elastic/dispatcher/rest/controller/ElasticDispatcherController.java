package com.github.richterplus.elastic.dispatcher.rest.controller;

import com.github.richterplus.elastic.dispatcher.*;
import com.github.richterplus.elastic.dispatcher.rest.bean.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("elasticDispatcher")
@RestController
public class ElasticDispatcherController {

    @Autowired
    private ElasticDispatcher elasticDispatcher;

    @PostMapping("test")
    @ApiOperation("测试")
    public TestResp test(@RequestBody @Validated TestReq req) {
        String workerPrefix = "worker_" + RandomStringUtils.randomAlphabetic(4) + "_";
        for (int i = 0; i < req.getWorkers(); i++) {

            Worker worker = new Worker();
            worker.setName(workerPrefix + i);
            worker.setMaxThreads(RandomUtils.nextInt(req.getMinWorkerThreads(), req.getMaxWorkerThreads() + 1));

            elasticDispatcher.createWorker(worker);

            String jobPrefix = "job_" + RandomStringUtils.randomAlphabetic(4) + "_";
            int jobCount = RandomUtils.nextInt(req.getMinJobsPerWorker(), req.getMaxJobsPerWorker() + 1);
            List<String> jobNos = new ArrayList<>(jobCount);
            List<String> jobInfos = new ArrayList<>(jobCount);
            for (int j = 0; j < jobCount; j++) {
                jobNos.add(jobPrefix + j);
                jobInfos.add(jobPrefix + j + "_info");
            }

            elasticDispatcher.assignJob(JobAssign
                            .multiJobs(worker.getName(), jobNos, jobInfos, req.getExpectedStart())
                            .acceptableStart(req.getAcceptableStart())
                            .maxRetry(req.getMaxRetry())
                            .timeout(req.getTimeout()));
        }
        return new TestResp();
    }

    /**
     * 统计数据
     */
    @GetMapping("statistics")
    @ApiOperation("统计数据")
    public Statistics statistics() {
        return elasticDispatcher.statistics();
    }

    /**
     * 任务执行
     */
    @PostMapping("onJobRun")
    @ApiOperation("任务执行")
    public Job onJobRun(@RequestBody Job job) {
        LoggerFactory.getLogger("").info("Job " + job.getJobUuid() + " started.");
        try {
            Thread.sleep(RandomUtils.nextInt(5000, 10000));
            elasticDispatcher.finishJob(job.getJobUuid());
            LoggerFactory.getLogger("").info("Job " + job.getJobUuid() + " finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return job;
    }

    /**
     * 任务失败
     */
    @PostMapping("onJobFail")
    @ApiOperation("任务失败")
    public Job onJobFail(@RequestBody Job job) {
        LoggerFactory.getLogger("").info("Job " + job.getJobUuid() + " failed.");
        return job;
    }

    /**
     * 注册任务执行监听
     */
    @PutMapping("listener")
    @ApiOperation("注册任务执行监听")
    public RegisterListenerResp registerListener(@RequestBody @Validated RegisterListenerReq req) {
        elasticDispatcher.onJobStart(this::onJobRun);
        elasticDispatcher.onJobFail(this::onJobFail);
        return new RegisterListenerResp();
    }

    /**
     * 创建任务
     */
    @PutMapping("job")
    @ApiOperation("创建任务")
    public CreateJobResp createJob(@RequestBody @Validated CreateJobReq req) {
        JobAssign jobAssign = JobAssign.singleJob(req.getWorkerName(), req.getJobNo(), req.getJobInfo(), req.getExpectedStart())
                .acceptableStart(req.getAcceptableStart())
                .maxRetry(req.getMaxRetry())
                .timeout(req.getTimeout());

        elasticDispatcher.assignJob(jobAssign);

        return new CreateJobResp() {
            {
                setJobUuid(jobAssign.getJobs().get(0).getJobUuid());
            }
        };
    }

    /**
     * 批量创建任务
     */
    @PutMapping("jobs")
    @ApiOperation("批量创建任务")
    public CreateJobsResp createJobs(@RequestBody @Validated CreateJobsReq req) {
        List<String> jobNos = new LinkedList<>();
        List<String> jobInfos = new LinkedList<>();
        for (CreateJobsReq.SingleJob job : req.getJobs()) {
            jobNos.add(job.getJobNo());
            jobInfos.add(job.getJobInfo());
        }

        JobAssign jobAssign = JobAssign.multiJobs(req.getWorkerName(), jobNos, jobInfos, req.getExpectedStart())
                .acceptableStart(req.getAcceptableStart())
                .maxRetry(req.getMaxRetry())
                .timeout(req.getTimeout());
        elasticDispatcher.assignJob(jobAssign);

        return new CreateJobsResp() {
            {
                setJobUuids(jobAssign.getJobs().stream().map(Job::getJobUuid).collect(Collectors.toList()));
            }
        };
    }

    /**
     * 创建工作者
     */
    @PutMapping("worker")
    @ApiOperation("创建工作者")
    public CreateWorkerResp createWorker(@RequestBody @Validated CreateWorkerReq req) {
        elasticDispatcher.createWorker(new Worker() {
            {
                setName(req.getName());
                setMaxThreads(req.getMaxThreads());
            }
        });

        return new CreateWorkerResp();
    }
}
