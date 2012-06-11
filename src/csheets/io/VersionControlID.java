package csheets.io;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author MpApQ
 */
public class VersionControlID implements Serializable{

    private String m_filename;
    private java.sql.Timestamp m_timestamp;

    public VersionControlID() {
    }

    public VersionControlID(String fn, java.sql.Timestamp ts) {
        m_filename = fn;
        m_timestamp = ts;
    }

    /**
     * @return the m_filename
     */
    public String getM_filename() {
        return m_filename;
    }

    /**
     * @param m_filename the m_filename to set
     */
    public void setM_filename(String m_filename) {
        this.m_filename = m_filename;
    }

    /**
     * @return the m_timestamp
     */
    public java.sql.Timestamp getM_timestamp() {
        return m_timestamp;
    }

    /**
     * @param m_timestamp the m_timestamp to set
     */
    public void setM_timestamp(java.sql.Timestamp m_timestamp) {
        this.m_timestamp = m_timestamp;
    }
}
