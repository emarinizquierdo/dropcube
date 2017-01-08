package com.dropcube.tasks;


import java.util.Enumeration;

public interface ITaskContext {
    String getParameter(String var1);

    String getRequiredParameter(String var1);

    String getHeader(String var1);

    Enumeration getHeaderNames();

    String getRequestURI();
}
