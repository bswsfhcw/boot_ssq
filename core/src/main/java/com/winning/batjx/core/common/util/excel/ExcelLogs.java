package com.winning.batjx.core.common.util.excel;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * The <code>ExcelLogs</code>
 */
public class ExcelLogs {
    private Boolean hasError;
    private List<ExcelLog> logList = new ArrayList<>();

    /**
     * 
     */
    public ExcelLogs() {
        super();
        hasError = false;
    }

    /**
     * @return the hasError
     */
    public Boolean getHasError() {
        return hasError;
    }

    /**
     * @param hasError
     *            the hasError to set
     */
    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }

    public List<ExcelLog> getLogList() {
        return new ArrayList<>(logList);
    }

    public void setLogList(List<ExcelLog> logList) {
        this.logList.clear();
        this.logList.addAll(logList);
    }

    /**
     * @return the logList
     */

    public List<ExcelLog> getErrorLogList() {
        List<ExcelLog> errList = new ArrayList<ExcelLog>();
        for (ExcelLog log : this.logList) {
            if (log != null && StringUtils.isNotBlank(log.getLog())) {
                errList.add(log);
            }
        }
        return errList;
    }

}
