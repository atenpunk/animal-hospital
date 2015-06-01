package co.th.aten.football.util;

import co.th.aten.football.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileCreator {

    static final String ROOT_PERSIST_PATH = "/PERSIST";
    static final String ROOT_REPLICATE_PATH = "/REPLICATED";
    static final String BOJPath = "/D_BJOB";
    static final String EOJPath = "/D_EJOB";
    static final String TIMEPath = "/D_SLOT";
    static final String FormatPath = "/D_FRMTCARD";
    static final String TRANSACTIONPath = "/D_ITRX";
    static final String ExceptionPath = "/D_ALRM";
    static final String TransferPath = "/TRANSFER";
    static final String ParameterPath = "/PARAMETER";
    static final String Unknown = "/UNKNOWN";
    static final String LaneParameter = "/LANEPARAM";
    static final String OBU_PATH = "/OBU";
    static final String BLACKLIST_PATH = "/BLACKLIST";
    static final String DBERROR_PATH = "/DBERROR";
    static final String DATA_PATH = "/DATA";
    // private Properties props;
    private final Log logger = LogFactory.getLog(getClass());

    public FileCreator() {
        // String jbosspath = System.getProperty("jboss.server.home.dir");
		/*
         * props = new Properties(); props.load(new FileInputStream(jbosspath +
         * "/conf/com.qfree.eta.mtc.receiver/receiverProperty.properties"));
         */
    }

    public void createPersistFile(String content, String filename, int transactionType,
            boolean status, Date date) throws Exception {
        StringBuffer buf = createPath();
        buf.append(ROOT_PERSIST_PATH);
        appendStatus(buf, status, date);
        appendFolder(buf, transactionType);

        File dir = new File(buf.toString());
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                logger.info("SUCCESS CREATING " + dir.getAbsolutePath());
            }
        }

        buf.append("/");
        buf.append(filename);
        FileOutputStream out;
        PrintStream p;
        out = new FileOutputStream(buf.toString());
        p = new PrintStream(out);
        p.print(content);
        p.close();

        if (status) {
            deleteFile(transactionType, 1, filename);
        }
    }

    public void createPersistFile(File file, short transactionType, boolean status, Date date) throws Exception {
        StringBuffer buf = createPath();
        buf.append(ROOT_PERSIST_PATH);

        appendStatus(buf, status, date);
        appendFolder(buf, transactionType);

        File dir = new File(buf.toString());
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                logger.info("SUCCESS CREATING " + dir.getAbsolutePath());
            }
        }

        buf.append("/");
        buf.append(file.getName());

        File dest = new File(buf.toString());

        copyFile(file, dest);

        if (status) {
            deleteFile(transactionType, 1, file.getName());
        }
    }

    public void copyFile(File source, File dest) {
        FileInputStream reader = null;
        FileOutputStream writer = null;
        try {
            reader = new FileInputStream(source);
            writer = new FileOutputStream(dest);
            // buffer 600Kb
            byte[] buffer = new byte[614400];
            int bytecount;
            while ((bytecount = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, bytecount);
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
        }

    }

    public void deleteFile(int transactionType, int dirtype, String filename) throws Exception {
        StringBuffer buf = createPath();

        if (dirtype == 1) {
            buf.append(ROOT_PERSIST_PATH);
        } else if (dirtype == 2) {
            buf.append(ROOT_REPLICATE_PATH);
        }

        buf.append("/FAIL");
        appendFolder(buf, transactionType);

        File dir = new File(buf.toString());
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                logger.info("SUCCESS CREATING " + dir.getAbsolutePath());
            }
        }

        buf.append("/");
        buf.append(filename);
        File todelete = new File(buf.toString());
        if (todelete.exists()) {
            if (todelete.delete()) {
                logger.info("deleted file from fail folder");
            } else {
                logger.info("Fail to delete file from fail folder : " + todelete.getAbsolutePath());
            }
        }
    }

    public List<String> getFileNameList(int type, int dirtype) {
        StringBuffer buf = new StringBuffer();
        buf.append(System.getProperty("user.dir"));
        List<String> filelist = new ArrayList<String>();

        if (dirtype == 1) {
            buf.append(ROOT_PERSIST_PATH);
        } else if (dirtype == 2) {
            buf.append(ROOT_REPLICATE_PATH);
        }

        buf.append("/FAIL");
        appendFolder(buf, type);
        File dir = new File(buf.toString());
        if (dir.exists()) {
            File[] file = dir.listFiles();
            for (int i = 0; i < file.length; i++) {
                filelist.add(file[i].getName());
            }
        }
        return filelist;
    }

    public String openFile(String filename, int transactionType, long reason) throws Exception {
        StringBuffer XMLContent = new StringBuffer();
        StringBuffer buf = new StringBuffer();
        buf.append(System.getProperty("user.dir"));
        if (reason == 1L) {
            buf.append(ROOT_PERSIST_PATH);
        } else if (reason == 2L) {
            buf.append(ROOT_REPLICATE_PATH);
        }

        buf.append("/FAIL");

        appendFolder(buf, transactionType);

        buf.append("/" + filename);
        File file = new File(buf.toString());
        BufferedReader input = null;
        String line = "";
        input = new BufferedReader(new FileReader(file));
        try {
            while ((line = input.readLine()) != null) {
                XMLContent.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        input.close();
        return XMLContent.toString();
    }

    public void createTransferFile(InputStream data, String filename, int transactionType, boolean status, Date date) throws Exception {
        StringBuffer buf = createPath();
        buf.append(ROOT_REPLICATE_PATH);
        appendStatus(buf, status, date);
        appendFolder(buf, transactionType);

        File dir = new File(buf.toString());
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                logger.info("SUCCESS CREATING " + dir.getAbsolutePath());
            }
        }

        buf.append("/");
        buf.append(filename);
        FileOutputStream out;
        out = new FileOutputStream(buf.toString());
        int d = 0;
        while ((d = data.read()) != -1) {
            out.write(d);
        }
        out.close();
//		data.close();
        System.out.println("created transfer file " + buf.toString());
        if (status) {
            System.out.println("try delete fail transfer file " + filename);
            deleteFile(transactionType, 2, filename);
        }
    }

    private StringBuffer createPath() {
        StringBuffer buf = new StringBuffer();
        // int statusTx = 0;
        buf.append(System.getProperty("user.dir"));
        return buf;
    }

    private void appendStatus(StringBuffer buf, boolean status, Date date) {
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd", Locale.US);
        SimpleDateFormat sdft = new SimpleDateFormat("HH");
        String strdate = sdfd.format(date);
        String strhh = sdft.format(date);
        if (status) {
            buf.append("/SUCCESS/" + strdate + "/" + strhh);
            // statusTx = 1;
        } else {
            buf.append("/FAIL");
        }
    }

    private void appendFolder(StringBuffer buf, int transactionType) {
        switch (transactionType) {
            case Constants.FILE_TYPE_TRANSACTION:
                buf.append(TRANSACTIONPath);
                break;
            case Constants.FILE_TYPE_BLACKLIST:
                buf.append(BLACKLIST_PATH);
                break;
            case Constants.FILE_TYPE_OBU:
                buf.append(OBU_PATH);
                break;
            case Constants.FILE_TYPE_DBERROR:
                buf.append(DBERROR_PATH);
                break;
            case Constants.FILE_TYPE_PARAMETER:
                buf.append(ParameterPath);
                break;
            case Constants.FILE_TYPE_DATA:
                buf.append(DATA_PATH);
                break;
            default:
                buf.append(Unknown);
                break;
        }
    }

    public void putInToInbox(String content, String filename, String folder) throws FileNotFoundException {
        try {
            // String location = Util.getReceiverConfig().getFileLocation();
            String location = System.getProperty("user.dir");
			FileOutputStream out;
            File dir = new File(location + folder);
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    logger.info("SUCCESS CREATING " + dir.getAbsolutePath());
                }
            }
            File file = new File(location + folder + filename);
            PrintStream p;
            out = new FileOutputStream(file);
            p = new PrintStream(out);
            p.print(content);
            p.close();
        } catch (IOException e) {
            logger.warn("Create xml file error : " + filename);
            e.printStackTrace();
        }
    }

    public void putInToOutbox(String content, String filename, String folder) throws FileNotFoundException {
        try {
            // String location = Util.getReceiverConfig().getFileLocation();
            String location = System.getProperty("user.dir");
			FileOutputStream out;
            File dir = new File(location + folder);
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    logger.info("SUCCESS CREATING " + dir.getAbsolutePath());
                }
            }
            File file = new File(location + folder + filename);
            PrintStream p;
            out = new FileOutputStream(file);
            p = new PrintStream(out);
            p.print(content);
            p.close();
        } catch (IOException e) {
            logger.warn("Create xml file error : " + filename);
        }
    }

    public void putInToOutbox(File file, String folder) throws FileNotFoundException {

        // String location = Util.getReceiverConfig().getFileLocation();
        String location = System.getProperty("user.dir");
		FileOutputStream out;
        File dir = new File(location + folder);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                logger.info("SUCCESS CREATING " + dir.getAbsolutePath());
            }
        }
        File dest = new File(location + folder + file.getName());
        copyFile(file, dest);

    }

    // --- put in outbox
    public void putInAlarmOutbox(String content, String filename) throws FileNotFoundException {
        putInToOutbox(content, filename, Constants.OUTBOX_ALARM);
    }

    public void putInBojOutbox(String content, String filename) throws FileNotFoundException {
        putInToOutbox(content, filename, Constants.OUTBOX_BOJ);
    }

    public void putInEojOutbox(String content, String filename) throws FileNotFoundException {
        putInToOutbox(content, filename, Constants.OUTBOX_EOJ);
    }

    public void putInTrxOutbox(String content, String filename) throws FileNotFoundException {
        putInToOutbox(content, filename, Constants.OUTBOX_TRX);
    }

    public void putInTimeSlotOutbox(String content, String filename) throws FileNotFoundException {
        putInToOutbox(content, filename, Constants.OUTBOX_TIMESLOT);
    }

    public void putInParameterOutbox(String content, String filename) throws FileNotFoundException {
        putInToOutbox(content, filename, Constants.OUTBOX_PARAMETER);
    }

    public void putInDataOutbox(String content, String filename) throws FileNotFoundException {
        putInToOutbox(content, filename, Constants.OUTBOX_DATA);
    }

    // --
    public void putInTrxOutbox(File file) throws FileNotFoundException {
        putInToOutbox(file, Constants.OUTBOX_TRX);
    }

    // ---- put in inbox

    public void putInParameterInbox(String content, String filename) throws FileNotFoundException {
        putInToInbox(content, filename, Constants.INBOX_PARAMETER);
    }

    public void putInDataInbox(String content, String filename) throws FileNotFoundException {
        putInToInbox(content, filename, Constants.INBOX_DATA);
    }

    public void createOutputFile(String content, String filename) throws FileNotFoundException {
        // String location = Util.getReceiverConfig().getFileLocation();
        String location = System.getProperty("user.dir");

        FileOutputStream out;
        PrintStream p;
        out = new FileOutputStream(location + "/" + filename);
        p = new PrintStream(out);
        p.print(content);
        p.close();
    }
    // private boolean isFailedFileExist( String filename ) {
    // ReceiverConfigBean receiverConfig = (ReceiverConfigBean)
    // Component.getInstance(
    // ReceiverConfigBean.class, true);
    // // String location = Util.getReceiverConfig().getFileLocation();
    // final String rootPath = receiverConfig.getFileLocation();
    // // final String rootPath = Util.getReceiverConfig().getFileLocation();
    // File xmlfile = new File(rootPath + "/FailedData.xml");
    // boolean isExist = false;
    // if (xmlfile.exists()) {
    // JAXBContext jc = null;
    // try {
    // jc = JAXBContext.newInstance(PersistedFiles.class);
    // } catch (JAXBException e2) {
    // // TODO Auto-generated catch block
    // e2.printStackTrace();
    // }
    //
    // try {
    // PersistedFiles persistedfiles;
    // Unmarshaller unmarshaller = jc.createUnmarshaller();
    // persistedfiles = (PersistedFiles) unmarshaller.unmarshal(xmlfile);
    // for( PersistedFiles.File failedfile : persistedfiles.getFile() ) {
    // if (failedfile.getName().equals(filename)) {
    // isExist = true;
    // break;
    // }
    // }
    // } catch (JAXBException e3) {
    // // TODO Auto-generated catch block
    // e3.printStackTrace();
    // }
    // }
    // return isExist;
    // }
    // private void createFailedXML( String filename, String path, int type, int
    // txType, int status
    // ) {
    // ReceiverConfigBean receiverConfig = (ReceiverConfigBean)
    // Component.getInstance(
    // ReceiverConfigBean.class, true);
    // // String location = Util.getReceiverConfig().getFileLocation();
    // final String rootPath = receiverConfig.getFileLocation();
    // // final String rootPath = Util.getReceiverConfig().getFileLocation();
    //
    // File xmlfile = new File(rootPath + "/FailedData.xml");
    // JAXBContext jc = null;
    // List<PersistedFiles.File> filelist = new
    // ArrayList<PersistedFiles.File>();
    // PersistedFiles persistedfiles = new PersistedFiles();
    // boolean isExist = false;
    // try {
    // jc = JAXBContext.newInstance("com.qfree.eta.mtc.receiver.persistfile");
    // } catch (JAXBException e2) {
    // // TODO Auto-generated catch block
    // e2.printStackTrace();
    // }
    // if (xmlfile.exists()) {
    // try {
    // Unmarshaller unmarshaller = jc.createUnmarshaller();
    // persistedfiles = (PersistedFiles) unmarshaller.unmarshal(xmlfile);
    // for( PersistedFiles.File failedfile : persistedfiles.getFile() ) {
    // if (failedfile.getName().equals(filename)) {
    // failedfile.setRetry(failedfile.getRetry() + 1);
    // failedfile.setStatus(status);
    // isExist = true;
    // }
    // filelist.add(failedfile);
    // }
    // } catch (JAXBException e3) {
    // // TODO Auto-generated catch block
    // e3.printStackTrace();
    // }
    // }
    //
    // if (!isExist) {
    // PersistedFiles.File failedfile = new PersistedFiles.File();
    // failedfile.setName(filename);
    // failedfile.setPath(path);
    // failedfile.setType(type);
    // failedfile.setTransactionType(txType);
    // failedfile.setRetry(0);
    // failedfile.setStatus(status);
    // persistedfiles.getFile().add(failedfile);
    // } else {
    // persistedfiles.setFile(filelist);
    // }
    //
    // Marshaller marshaller = null;
    // try {
    // marshaller = jc.createMarshaller();
    // } catch (JAXBException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // // specify output behavior
    // try {
    // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new
    // Boolean(true));
    // } catch (PropertyException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // // write out xml
    // try {
    // marshaller.marshal(persistedfiles, new FileOutputStream(rootPath +
    // "/FailedData.xml"));
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (JAXBException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
}
