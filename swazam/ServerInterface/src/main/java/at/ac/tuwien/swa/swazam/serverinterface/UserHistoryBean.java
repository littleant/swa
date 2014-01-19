package at.ac.tuwien.swa.swazam.serverinterface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import at.ac.tuwien.swa.swazam.server.UserInfo;
import at.ac.tuwien.swa.swazam.server.Main;
import at.ac.tuwien.swa.swazam.server.UserInfoRepo;
import at.ac.tuwien.swa.swazam.server.UserRequestHistory;
import at.ac.tuwien.swa.swazam.server.UserRequestHistoryRepo;
import javax.faces.bean.ManagedProperty;
/**
 *
 * @author 19992_000
 */
@ManagedBean(name="userHistory")
@NoneScoped
public class UserHistoryBean implements Serializable{

    private static final long serialVersionUID = 1L;
    public long id;
    private UserInfoRepo repo = new UserInfoRepo();
    private UserRequestHistoryRepo histRepo = new UserRequestHistoryRepo();
    public UserRequestHistory getUserHistoryList(long id) {
		return histRepo.read(id);
    }
    public UserInfo[] getUserList() {
		return repo.read();
    }
    public UserRequestHistory[] getAllUserHistoryList() {
		return histRepo.readAll();
    }
    public String goHistPage(long id){
        this.id = id;
        return "history";
    }
    public long getCurrUser(){
        return this.id;
    }
    public void setCurrUser(long id){
        this.id = id;
    }
}
