package at.ac.tuwien.swa.swazam.server;


import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;




@Entity
@SequenceGenerator(name = "seq", sequenceName = "id_seq")
public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
        @GeneratedValue(strategy=GenerationType.AUTO,generator="seq")
	private long id;
        @Column
	private String userName;
	@Column
        private String loginName;
        @Column
        private long coins;
        public long getCoins() {
		return coins;
	}
	public void setCoins(long coins) {
		this.coins = coins;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String name) {
		this.userName = name;
	}
        public String getLogin() {
		return this.loginName;
	}
	public void setLogin(String loginName) {
		this.loginName = loginName;
	}
}
