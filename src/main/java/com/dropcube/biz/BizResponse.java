package com.dropcube.biz;

import javax.servlet.ServletOutputStream;

import com.dropcube.beans.Response;

import javax.servlet.ServletOutputStream;
import java.util.Map;

/**
 * @author CIBER 2013
 * @version 2.0
 */
public class BizResponse {

    private String contentType = "text/plain;charset=UTF-8";

    private String characterSet = "text/plain;charset=UTF-8";

    private Response response = null;

    private byte[] responseContent = null;

    private String responseUrlRedirect = null;

    private Map<String, String> header;

    private long dateHeader;

    public BizResponse() {
        super();
        response = new Response();
    }

    public BizResponse(int code, String message) {
        this.response = new Response(code, message);
    }

    public BizResponse(int code, String message, Object data) {
        this.response = new Response(code, message, data);
    }

    /**
     * Constructor by parameters
     *
     * @param contentType  Content type of the response.
     * @param characterSet Charset of the response.
     * @param data         Information retrieved.
     * @param outputStream Stream retrieved
     * @param header       Map with all headers retreived.
     * @param dateHeader
     */
    public BizResponse(String contentType, String characterSet, Object data, ServletOutputStream outputStream, Map<String, String> header, long dateHeader) {
        super();
        this.contentType = contentType;
        this.characterSet = characterSet;
        this.response = new Response(data);
        this.header = header;
        this.dateHeader = dateHeader;
    }

    /**
     * @param data
     */
    public BizResponse(Object data) {
        super();
        this.response = new Response(data);

    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the characterSet
     */
    public String getCharacterSet() {
        return characterSet;
    }

    /**
     * @param characterSet the characterSet to set
     */
    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }

    /**
     * @return the response
     */
    public Response getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(Object response) {
        this.response = new Response(response);
    }


    /**
     * @return the header
     */
    public Map<String, String> getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    /**
     * @return the dateHeader
     */
    public long getDateHeader() {
        return dateHeader;
    }

    /**
     * @param dateHeader the dateHeader to set
     */
    public void setDateHeader(long dateHeader) {
        this.dateHeader = dateHeader;
    }

    /**
     * @return the responseContent
     */
    public byte[] getResponseContent() {
        return responseContent;
    }

    /**
     * @param responseContent the responseContent to set
     */
    public void setResponseContent(byte[] responseContent) {
        this.responseContent = responseContent;
    }

    public String toString() {
        if (response != null)
            return (String) response.toString();

        return null;
    }

    /**
     * @return URL to redirect
     */
    public String getResponseUrlRedirect() {
        return responseUrlRedirect;
    }

    /**
     * @param responseUrlRedirect the URL to redirect
     */
    public void setResponseUrlRedirect(String responseUrlRedirect) {
        this.responseUrlRedirect = responseUrlRedirect;
    }

    /**
     * Parsers the response to a json without Expose Annotation fields.
     *
     * @return String Json object
     */
    public String toJsonExcludeFieldsWithoutExposeAnnotation() {
        if (this.response != null)
            return this.response.toJsonExcludeFieldsWithoutExposeAnnotation();
        else
            return null;
    }

    /**
     * Parsers the response to a json.
     *
     * @return String Json object
     */
    public String toJson() {
        if (this.response != null)
            return this.response.toJson();
        else
            return null;
    }
}
/** Consultants in Business Engineering Research, S.L.U. 2013 */