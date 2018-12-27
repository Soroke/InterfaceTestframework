package net.faxuan.objectInfo.excel;

/**
 * Created by song on 2018/12/27.
 */
public class Excel {
    private InterfaceInfo interfaceInfo;
    private CaseCheck caseCheck;
    private DBCheck dbCheck;

    public InterfaceInfo getInterfaceInfo() {
        return interfaceInfo;
    }

    public void setInterfaceInfo(InterfaceInfo interfaceInfo) {
        this.interfaceInfo = interfaceInfo;
    }

    public CaseCheck getCaseCheck() {
        return caseCheck;
    }

    public void setCaseCheck(CaseCheck caseCheck) {
        this.caseCheck = caseCheck;
    }

    public DBCheck getDbCheck() {
        return dbCheck;
    }

    public void setDbCheck(DBCheck dbCheck) {
        this.dbCheck = dbCheck;
    }
}
