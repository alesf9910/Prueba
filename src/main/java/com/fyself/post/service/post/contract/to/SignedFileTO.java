package com.fyself.post.service.post.contract.to;

public class SignedFileTO {
    private byte[] signedFileS3;
    private String typeFile;

    public byte[] getSignedFileS3() {
        return signedFileS3;
    }

    public void setSignedFileS3(byte[] signedFileS3) {
        this.signedFileS3 = signedFileS3;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }
}
