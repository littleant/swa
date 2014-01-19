/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.swa.swazam.serverinterface;

import at.ac.tuwien.swa.swazam.server.Main;
import at.ac.tuwien.swa.swazam.server.UserInfo;
import at.ac.tuwien.swa.swazam.server.UserInfoRepo;
import at.ac.tuwien.swa.swazam.server.UserRequestHistoryRepo;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author 19992_000
 */
@ManagedBean(name="userBean")
@RequestScoped
public class UserBean {
    /**
     * Creates a new instance of UserBean
     */
    private UserInfoRepo repo = new UserInfoRepo();
    private UserRequestHistoryRepo histRepo = new UserRequestHistoryRepo();
    
    public UserBean() {
    }
    public void deleteUser(long id){
        UserInfo user = repo.readOne(id);
        repo.delete(user);
    }
    public String goModifyPage(){
        return "modify";
    }
    public String createUser(String id){
        UserInfo n = new UserInfo();
        n.setUserName(id);
        repo.insert(n);
        return "index";
    }
    public long getCoins(long id){
        return repo.readOne(id).getCoins();
    }
    public String update(long id,long coins){
        UserInfo user = repo.readOne(id);
        repo.update(user);
        return "index";
    }
}
