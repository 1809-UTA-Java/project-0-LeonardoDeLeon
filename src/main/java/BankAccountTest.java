package main.java;

//import org.junit.Test;
import org.junit.*;

public class BankAccountTest {
	
	BankAccount testAccount = new BankAccount();
	
	@Test
	public void testDeposit() {
		//replace fail with
		Assert.assertEquals("normal positive value test",testAccount.deposit(50),true);
	}
	
    @Test
    public void testDepositWithNegative() {
        //replace fail with
        Assert.assertEquals("for negative values",testAccount.deposit(-50),true);
    }
    
    @Test(expected = NullPointerException.class)
    public void testDepositNullAmt() {
        Assert.assertEquals(testAccount.deposit(0),true);  // expect it to fail     
    }
    
    @Test
    public void testWithdrawal() {
    	Assert.assertEquals("normal positive  value test",testAccount.deposit(50),true);
    }

    @Test
    public void testWithdrawalWithNegative() {
        //replace fail with
        Assert.assertEquals("for negative values",testAccount.deposit(-50),true);
    }
    
    @Test(expected = NullPointerException.class)
    public void testWithdrawalNullAmt() {
        Assert.assertEquals(testAccount.deposit(0),true);  // expect it to fail     
    }    
  
}
