package com.dropcube.exceptions;

/**
 * Created by edu on 7/01/17.
 */
public class DropcubeException extends Exception {

    public static final String ERROR = "error";
    public static final String WARNING = "warning";
    private int code = -1;
    private String level = "error";

    public DropcubeException(int code) {
        this.code = code;
    }

    public DropcubeException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public DropcubeException(String message, int code) {
        super(message);
        this.code = code;
    }

    public DropcubeException(String message) {
        super(message);
    }

    public DropcubeException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public DropcubeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DropcubeException(String message, String level) {
        super(message);
        this.level = level;
    }

    public int getCode() {
        return this.code;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFormattedMessage() {
        return this.getFormatedMessage("json");
    }

    public String getFormatedMessage(String format) {
        if(format != null) {
            StringBuilder output = new StringBuilder();
            if("json".equals(format.toLowerCase())) {
                output.append("{\"");
                output.append(this.level);
                output.append("\":\"");
                output.append(super.getMessage());
                output.append("\"}");
            }

            if("xml".equals(format.toLowerCase())) {
                output.append("<" + this.level + ">");
                output.append(super.getMessage());
                output.append("</" + this.level + ">");
            }

            return output.toString();
        } else {
            return null;
        }
    }

    public String toString() {
        String className = this.getClass().getName();
        String message = this.getLocalizedMessage();
        StringBuilder buffer = new StringBuilder();
        buffer.append(className);
        if(message != null) {
            buffer.append(": ").append(message);
        }

        if(this.code != -1) {
            buffer.append(" (#").append(this.code).append("#)");
        }

        return buffer.toString();
    }

}
