package com.dropcube.tasks;

import com.dropcube.beans.Device;
import com.dropcube.beans.User;
import com.dropcube.biz.DeviceBiz;
import com.dropcube.constants.Params;
import com.dropcube.exceptions.DropcubeException;
import com.dropcube.tasks.impl.Task;
import com.dropcube.tasks.ITaskContext;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by edu on 8/01/17.
 */
public class RefreshDevices extends Task {

    private static User user = new User();
    private final static Logger LOGGER = Logger.getLogger(RefreshDevices.class.getName());



    public void run(ITaskContext context){
        long l0 = System.currentTimeMillis();

        LOGGER.info("INI");

        Long processTime = null;
        processTime = new Long(System.currentTimeMillis());
        LOGGER.info(" Add control mark");

        System.out.println("Doing an expensive operation...");
        user.setEmail(Params.ADMIN_USER);
        DeviceBiz DEVICE_BIZ = new DeviceBiz(user);
        List<Device> devices = DEVICE_BIZ.getAll();

        try {

            for (Device device : devices) {
                DEVICE_BIZ.refreshDevice(device.getId());
            }

        }catch(DropcubeException e){
             LOGGER.warning("Exception running task" + e.getMessage());
        }

        LOGGER.info("Devices refershed: " + devices.size() + ". END " + (System.currentTimeMillis() - l0) + " ms");
    }

    public static void launch() {

        try {

            Queue q = QueueFactory.getDefaultQueue();
            TaskOptions to = TaskOptions.Builder.withUrl("");

/*
            if (fileName != null && !fileName.isEmpty()) {
                to.param(Params.PARAM_FILE_NAME, fileName);
                to.param(Params.PARAM_FILE_NAME, fileName);
            }
*/
            q.add(to);

        } catch (Exception e) {
            LOGGER.warning("Error launching task");
        }

    }
}
