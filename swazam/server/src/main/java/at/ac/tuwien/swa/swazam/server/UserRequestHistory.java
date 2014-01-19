/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.swa.swazam.server;

/**
 *
 * @author 19992_000
 */
import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq2", sequenceName = "id_seq2")
public class UserRequestHistory {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,generator="seq")
    private long id;
    @Column
    private long userName;
    @Column
    private String searchRequest;
    @Column
    private String status;
    @Column
    private String result;

    public UserRequestHistory() {
    }
    public UserRequestHistory(String search,long user,String status,String res){
        this.userName = user;
        this.searchRequest = search;
        this.status = status;
        this.result = res;
    }
    public void setStatus(String s){
        this.status = s;
    }
    public String getStatus(){
        return this.status;
    }
    public void setResult(String r){
        this.result = r;
    }
    public String getResult(){
        return this.result;
    }
    public void setSearch(String search){
        this.searchRequest = search;
    }
    public String getSearch(){
        return this.searchRequest;
    }
    public void setUser(long user){
        this.userName = user;
    }
    public long getUser(){
        return this.userName;
    }
}
