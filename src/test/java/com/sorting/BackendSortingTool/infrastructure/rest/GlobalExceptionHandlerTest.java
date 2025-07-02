package com.sorting.BackendSortingTool.infrastructure.rest;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Locale;
import java.security.Principal;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

class GlobalExceptionHandlerTest {
    @Test
    void handleValidationExceptions() {
        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        org.springframework.validation.BindingResult bindingResult = Mockito.mock(org.springframework.validation.BindingResult.class);
        org.springframework.validation.ObjectError error = new org.springframework.validation.ObjectError("object", "validation failed");
        Mockito.when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(error));
        Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);
        var response = handler.handleValidationExceptions(ex, request);
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("validation failed"));
    }
    @Test
    void constructorCoverage() {
        new GlobalExceptionHandler();
    }
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final WebRequest request = new DummyWebRequest();

    @Test
    void handleIllegalArgument() {
        var response = handler.handleIllegalArgument(new IllegalArgumentException("msg"), request);
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("msg"));
    }

    @Test
    void handleAllExceptions() {
        var response = handler.handleAllExceptions(new RuntimeException("err"), request);
        assertEquals(500, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("err"));
    }

    @Test
    void handleEntityNotFound() {
        var response = handler.handleEntityNotFound(new EntityNotFoundException("not found"), request);
        assertEquals(404, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("not found"));
    }

    @Test
    void handleHttpMessageNotReadable() {
        var response = handler.handleHttpMessageNotReadable(new HttpMessageNotReadableException("bad json"), request);
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("bad json"));
    }

    @Test
    void handleMethodNotSupported() {
        var response = handler.handleMethodNotSupported(new HttpRequestMethodNotSupportedException("POST"), request);
        assertEquals(405, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("MethodNotAllowed"));
    }

    static class DummyWebRequest implements WebRequest {
        public Object resolveReference(String key) { return null; }
        public void registerDestructionCallback(String name, Runnable callback, int scope) {}
        public String getDescription(boolean includeClientInfo) { return "test-path"; }
        public Object getAttribute(String name, int scope) { return null; }
        public void setAttribute(String name, Object value, int scope) {}
        public void removeAttribute(String name, int scope) {}
        public String[] getAttributeNames(int scope) { return new String[0]; }
        public String getHeader(String headerName) { return null; }
        public String[] getHeaderValues(String headerName) { return new String[0]; }
        public Iterator<String> getHeaderNames() { return Collections.emptyIterator(); }
        public String getParameter(String paramName) { return null; }
        public String[] getParameterValues(String paramName) { return new String[0]; }
        public Iterator<String> getParameterNames() { return Collections.emptyIterator(); }
        public Map<String, String[]> getParameterMap() { return Collections.emptyMap(); }
        public Locale getLocale() { return Locale.getDefault(); }
        public String getContextPath() { return null; }
        public String getRemoteUser() { return null; }
        public Principal getUserPrincipal() { return null; }
        public boolean isUserInRole(String role) { return false; }
        public boolean isSecure() { return false; }
        public boolean checkNotModified(long lastModifiedTimestamp) { return false; }
        public boolean checkNotModified(String etag) { return false; }
        public boolean checkNotModified(String etag, long lastModifiedTimestamp) { return false; }
        public String getSessionId() { return null; }
        public Object getSessionMutex() { return null; }
    }
}
