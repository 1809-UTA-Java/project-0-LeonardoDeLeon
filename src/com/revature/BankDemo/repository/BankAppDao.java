package com.revature.BankDemo.repository;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.revature.BankDemo.model.BankUsersAccount;
import com.revature.BankDemo.model.BankAccounts;
import com.revature.BankDemo.model.BankUsers;
import com.revature.BankDemo.util.DbConnUtil;

import oracle.jdbc.internal.OracleTypes;

public class BankAppDao {

	public List<BankUsers> getBankUsers() {
        PreparedStatement ps = null;
        
		BankUsers bu = null;
		List<BankUsers> bankUsers = new ArrayList<>();
		
		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "SELECT * FROM USERS";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int uid = rs.getInt("user_id");
				String username = rs.getString("username");
				String userPassword = rs.getString("password");
				int utId = rs.getInt("user_type_id");
				
				bu = new BankUsers(uid, username, userPassword, utId);
				bankUsers.add(bu);
			}
			
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
		
		return bankUsers;
	}

	public List<BankAccounts> getBankAccounts() {
        PreparedStatement ps = null;
        
		BankAccounts ba = null;
		List<BankAccounts> bankAccounts = new ArrayList<>();
		
		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "SELECT * FROM ACCOUNT";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int accountId = rs.getInt("account_id");
				int accountTypeId = rs.getInt("account_type_id");
				int accountStatusId = rs.getInt("account_status_id");
                int userId = rs.getInt("user_id");
                int balance = rs.getInt("account_balance");
				
                ba = new BankAccounts(accountId, accountTypeId, accountStatusId, userId, balance);
				bankAccounts.add(ba);
			}
			
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
		
		return bankAccounts;
	}

	public List<BankUsers> getBankUsersSp() {
		CallableStatement cs = null;
		
		BankUsers bu = null;
		List<BankUsers> bankUsers = new ArrayList<>();

		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "{ CALL GET_ALL_FROM_USERS(?) }";
			cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(1);
			while(rs.next()) {
				int uid = rs.getInt("user_id");
				String username = rs.getString("username");
				String userPassword = rs.getString("password");
				int utId = rs.getInt("user_type_id");
				
				bu = new BankUsers(uid, username, userPassword, utId);
				bankUsers.add(bu);
			}
			
			cs.close();
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bankUsers;
	}	

	public void registerNewUser(String username, String password) {
		
		CallableStatement cs = null;
		
		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "{CALL REGISTER_NEW_USER_SP(?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, username);
			cs.setString(2, password);
			
			cs.execute();
			cs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void submitApplication(int userId, int deposit) {
		
		CallableStatement cs = null;
		
		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "{CALL SUBMIT_APPLICATION_SP(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, userId);			
			cs.setInt(2, deposit);
			cs.execute();
			cs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateAccountBalance(int accountId, int deposit) {
		
		CallableStatement cs = null;
		
		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "{CALL UPDATE_ACCOUNT_BALANCE_SP(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, accountId);			
			cs.setInt(2, deposit);
			cs.execute();
			cs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addTransactionRecord(int accountId, int deposit) {
		
		CallableStatement cs = null;
		
		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "{CALL ADD_TRANSACTION_RECORD_SP(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, accountId);			
			cs.setInt(2, deposit);
			cs.execute();
			cs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void createUserAccount(int userId, int accountId) {
		
		CallableStatement cs = null;
		
		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "{CALL CREATE_USER_ACCOUNT_SP(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, userId);			
			cs.setInt(2, accountId);
			cs.execute();
			cs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	

	public List<BankUsersAccount> getBankUsersAccount() {
		CallableStatement cs = null;
		
		BankUsersAccount bua = null;
		List<BankUsersAccount> bankUsersAccount = new ArrayList<>();

		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "{ CALL GET_ALL_FROM_USERS_ACCOUNT_SP(?) }";
			cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(1);
			while(rs.next()) {
				int uid = rs.getInt("user_id");
				int aid = rs.getInt("account_id");
				Timestamp userAcctCreationTs = rs.getTimestamp("user_acct_creation_t_s");
				
				bua = new BankUsersAccount(uid, aid, userAcctCreationTs);
				bankUsersAccount.add(bua);
			}
			
			cs.close();
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bankUsersAccount;
	}

	public void approveAnApplicant(int accountId) {
		
		CallableStatement cs = null;
		
		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "{CALL APPROVE_AN_APPLICANT_SP(?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, accountId);
			cs.execute();
			cs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void denyAnApplicant(int accountId) {
		
		CallableStatement cs = null;
		
		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "{CALL DENY_AN_APPLICANT_SP(?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, accountId);
			cs.execute();
			cs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void main (String [] args) {
		BankAppDao bad = new BankAppDao();
		bad.registerNewUser("newUser","newPasword");
	}
}