package csheets.io;

/**
 *
 * @author MpApQ
 */
public class VersionControl {
    private VersionControlID m_key;
    private int m_id;
    private java.sql.Blob m_blob;
    private String m_annotation;

    VersionControl() {
    }

    VersionControl(VersionControlID key, int id, java.sql.Blob b) {
        m_key=key;
        m_id = id;
        m_blob = b;
    }
    
    VersionControl(VersionControlID key, java.sql.Blob b) {
        m_key=key;
        m_blob = b;
    }
    
    VersionControl(VersionControlID key, int id, java.sql.Blob b, String anno) {
        m_key=key;
        m_id = id;
        m_blob = b;
        m_annotation = anno;
    }
    
    

   
    /**
     * @return the m_id
     */
    public int getM_id() {
        return m_id;
    }

    /**
     * @param m_id the m_id to set
     */
    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    /**
     * @return the m_blob
     */
    public java.sql.Blob getM_blob() {
        return m_blob;
    }

    /**
     * @param m_blob the m_blob to set
     */
    public void setM_blob(java.sql.Blob m_blob) {
        this.m_blob = m_blob;
    }

    /**
     * @return the m_key
     */
    public VersionControlID getM_key() {
        return m_key;
    }

    /**
     * @param m_key the m_key to set
     */
    public void setM_key(VersionControlID m_key) {
        this.m_key = m_key;
    }

    /**
     * @return the m_annotation
     */
    public String getM_annotation() {
        return m_annotation;
    }

    /**
     * @param m_annotation the m_annotation to set
     */
    public void setM_annotation(String m_annotation) {
        this.m_annotation = m_annotation;
    }
}
