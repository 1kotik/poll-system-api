package by.kotik.analyticsservice.exception;

import exception.AppException;

public class AnalyticsException extends AppException {
    public AnalyticsException(String message) {
        super(400, "ANALYTIC_ERROR", message);
    }
}
