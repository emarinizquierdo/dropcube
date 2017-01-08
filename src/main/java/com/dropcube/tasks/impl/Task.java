package com.dropcube.tasks.impl;

import com.dropcube.tasks.ITaskContext;
import com.google.appengine.api.taskqueue.InternalFailureException;
import com.google.appengine.api.taskqueue.InvalidQueueModeException;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.appengine.api.taskqueue.TransientFailureException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Task extends HttpServlet {
    protected static final Logger LOGGER = Logger.getLogger(Task.class.getSimpleName());

    public Task() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskUrl = request.getRequestURI();

        try {
            String e = request.getHeader("X-AppEngine-QueueName");
            String taskName = request.getHeader("X-AppEngine-TaskName");
            String taskRetryCount = request.getHeader("X-AppEngine-TaskRetryCount");
            String failFast = request.getHeader("X-AppEngine-FailFast");
            LOGGER.info(String.format("Executing task %s, name = \'%s\', queue = \'%s\', retryCount = %s, failFast = %s, ...", new Object[]{taskUrl, taskName, e, taskRetryCount, failFast}));
            long before = System.currentTimeMillis();
            Task.ServletTaskContext context = new Task.ServletTaskContext(request);
            this.run(context);
            long after = System.currentTimeMillis();
            LOGGER.info(String.format("Task %s executed, time = %d milliseconds.", new Object[]{taskUrl, Long.valueOf(after - before)}));
        } catch (Exception var13) {
            LOGGER.log(Level.WARNING, var13.getMessage(), var13);
            throw new ServletException(var13.getMessage(), var13);
        }
    }

    public abstract void run(ITaskContext var1) throws Exception;

    public static List<TaskHandle> leaseTasks(Queue queue, long lease, TimeUnit unit, long countLimit, int numRetries) throws InvalidQueueModeException, InternalFailureException, TransientFailureException {
        LOGGER.info("Queue: " + (queue != null?queue.getQueueName():null));
        LOGGER.info("Lease: " + lease);
        LOGGER.info("Time unit: " + unit.toString());
        LOGGER.info("Count limit: " + countLimit);
        LOGGER.info("Num retries: " + numRetries);
        if(queue == null) {
            throw new InternalFailureException("Queue is NULL");
        } else {
            byte cont = 0;

            while(numRetries > cont) {
                try {
                    int cont1 = cont + 1;
                    return queue.leaseTasks(lease, unit, countLimit);
                } catch (TransientFailureException var11) {
                    LOGGER.log(Level.WARNING, var11.getMessage(), var11);

                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException var10) {
                        LOGGER.log(Level.WARNING, var10.getMessage(), var10);
                    }
                }
            }

            return null;
        }
    }

    private static class ServletTaskContext implements ITaskContext {
        private final HttpServletRequest request;

        public ServletTaskContext(HttpServletRequest request) {
            this.request = request;
        }

        public String getParameter(String name) {
            return this.request.getParameter(name);
        }

        public String getHeader(String name) {
            return this.request.getHeader(name);
        }

        public Enumeration getHeaderNames() {
            return this.request.getHeaderNames();
        }

        public String getRequestURI() {
            return this.request.getRequestURI();
        }

        public String getRequiredParameter(String name) {
            String value = this.getParameter(name);
            if(value == null) {
                throw new RuntimeException(String.format("Parameter %s is required.", new Object[]{name}));
            } else {
                return value;
            }
        }
    }
}
