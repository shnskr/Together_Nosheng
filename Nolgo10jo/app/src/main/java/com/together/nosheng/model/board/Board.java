package com.together.nosheng.model.board;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Board {

    private String documentId;//db삭제 할때 불러올 문서 Id
    private String docId;
    private String title;
    private String contents;
//    private String writer;@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private int docNumber;//문서아이디 숫자로 해볼려고 잠시,,

    @ServerTimestamp
    private Date date;

    public Board() {
    }

    public Board(String documentId,String docId, String title, String contents, Date date, int docNumber) {
        this.documentId = documentId;
        this.docId = docId;
        this.title = title;
        this.contents = contents;
//        this.writer = writer;@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        this.date = date;
        this.docNumber = docNumber;
    }
//
//    public int getDocNumber() {
//        return docNumber;
//    }
//
//    public void setDocNumber(int docNumber) {
//        this.docNumber = docNumber;
//    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Board{" +
                "documentId='" + documentId + '\'' +
                "docId='" + docId + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", date=" + date +
                '}';
    }
}

