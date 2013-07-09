package com.oasis.tmsv5.util.helper;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oasis.tmsv5.util.tools.DateGen;
import com.oasis.tmsv5.util.tools.LogMessageUtil;

public class FileStorageHelper {
    private static final Log log = LogFactory.getLog(FileStorageHelper.class);

    private static final String FILE_STORAGE = "fileStorage.properties";

    public static final String FILE_STORAGE_PATH = "file.storage.path";

    public static final String FILE_DOWNLOAD_URL = "file.download.url";

    public static final String SPLITOR = "/";

    public static final String REPORTDIR = "report";

    public static final String MESSAGEDIR = "message";

    public static final String OFFICAL_ATTACHMENT_DIR = "offical-attachment";
    
    public static final String EXCEL_FILE = "xls";
    
    public static final String PAYMENT_TEMPLATE = "Template_PaymentItem_Export.xml";
    
    public static final String FEE_ITEM_TEMPLATE = "Template_FeeItem_Export.xml";
    
    public static final String PAYMENTITEM_TEMPLATE_CLOSE = "PaymentItem_Template_Close_Export.xml";
    
    public static final String FEEITEM_TEMPLATE_CLOSE = "FeeItem_Template_Close_Export.xml";
    
    public static final String OPTIMERECORD_TEMPLATE = "Template_OperationTimeRecord_Export.xml";

    private static Configuration config;

    private static FileStorageHelper helper = new FileStorageHelper();

    private FileStorageHelper() {
        try {
            config = new PropertiesConfiguration(FILE_STORAGE);
        } catch (Exception e) {
            log.error(LogMessageUtil.printStack(e));
        }
    }

    public static FileStorageHelper getInstance() {
        return helper;
    }

    public String getValue(String key) {
        return config.getString(key);
    }

    synchronized public static File createFile(String fileName) {
        File storageDir = new File(FileStorageHelper.getInstance().getValue(FileStorageHelper.FILE_STORAGE_PATH)+getSubDir4OfficialAttachment());
        if(!storageDir.exists()){
        	storageDir.mkdirs();
        }
        File file = new File(storageDir, fileName);
        return file;
    }

    synchronized public static File createFile(String domainCode, String customerCode, String fileName) {
        File storageDir = new File(FileStorageHelper.getInstance().getValue(FileStorageHelper.FILE_STORAGE_PATH)
                + getSubDirByCustomer(domainCode, customerCode));
        File file = new File(storageDir, fileName);
        return file;
    }

    synchronized public static File createReportFile(String fileName) {
        File storageDir = new File(FileStorageHelper.getInstance().getValue(FileStorageHelper.FILE_STORAGE_PATH)
                + getSubDir4Report());
        File file = new File(storageDir, fileName);
        return file;
    }

    synchronized public static File createMessageFile(String fileName) {
        File storageDir = new File(FileStorageHelper.getInstance().getValue(FileStorageHelper.FILE_STORAGE_PATH)
                + getSubDir4Message());
        File file = new File(storageDir, fileName);
        return file;
    }

    synchronized public static String getDownloadURL(FileSource fileSource, String fileName) {
        String resultUrl = null;
        switch (fileSource) {
        case ATTACHMENT: {
            resultUrl = FileStorageHelper.getInstance().getValue(FileStorageHelper.FILE_DOWNLOAD_URL)
                    + getSubDir4OfficialAttachment() + SPLITOR + fileName;
            break;
        }

        case REPORT: {
            resultUrl = FileStorageHelper.getInstance().getValue(FileStorageHelper.FILE_DOWNLOAD_URL) + getSubDir4Report()
                    + SPLITOR + fileName;
            break;
        }
        case MESSAGE: {
            resultUrl = FileStorageHelper.getInstance().getValue(FileStorageHelper.FILE_DOWNLOAD_URL) + getSubDir4Message()
                    + SPLITOR + fileName;
            break;
        }

        }

        return resultUrl;
    }
    
    synchronized public static String getDownloadContextPath(FileSource fileSource) {
        String contextPath = null;
        switch (fileSource) {
        case ATTACHMENT: {
        	contextPath = FileStorageHelper.getInstance().getValue(FileStorageHelper.FILE_DOWNLOAD_URL);
            break;
        }

        case REPORT: {
        	contextPath = FileStorageHelper.getInstance().getValue(FileStorageHelper.FILE_DOWNLOAD_URL);
            break;
        }
        case MESSAGE: {
        	contextPath = FileStorageHelper.getInstance().getValue(FileStorageHelper.FILE_DOWNLOAD_URL);
            break;
        }

        }

        return contextPath;
    }

    synchronized public static String getFileName(String extName) {
        return System.currentTimeMillis() + "_" + UUID.randomUUID() + "." + extName;
    }

    private static String getSubDirByCustomer(String domainCode, String customerCode) {
        Calendar calendar = Calendar.getInstance();
        String today = DateGen.getStringDateByFormat(calendar.getTime(), "yyyyMMdd");
        return SPLITOR + domainCode + SPLITOR + customerCode + SPLITOR + today;
    }

    private static String getSubDir4Report() {
        Calendar calendar = Calendar.getInstance();
        String toMonth = DateGen.getStringDateByFormat(calendar.getTime(), "yyyyMM");
        return SPLITOR + REPORTDIR + SPLITOR + toMonth;
    }

    private static String getSubDir4Message() {
        Calendar calendar = Calendar.getInstance();
        String toMonth = DateGen.getStringDateByFormat(calendar.getTime(), "yyyyMM");
        return SPLITOR + MESSAGEDIR + SPLITOR + toMonth;
    }

    private static String getSubDir4OfficialAttachment() {
        Calendar calendar = Calendar.getInstance();
        String toMonth = DateGen.getStringDateByFormat(calendar.getTime(), "yyyyMM");
        return SPLITOR + OFFICAL_ATTACHMENT_DIR + SPLITOR + toMonth;
    }

}
